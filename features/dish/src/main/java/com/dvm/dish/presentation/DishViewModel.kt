package com.dvm.dish.presentation

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.dvm.db.api.CartRepository
import com.dvm.db.api.DishRepository
import com.dvm.db.api.FavoriteRepository
import com.dvm.dish.presentation.model.DishEvent
import com.dvm.dish.presentation.model.DishState
import com.dvm.navigation.Navigator
import com.dvm.network.api.MenuApi
import com.dvm.preferences.api.DatastoreRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

internal class DishViewModel(
    private val dishId: String,
    private val dishRepository: DishRepository,
    private val favoriteRepository: FavoriteRepository,
    private val cartRepository: CartRepository,
    private val menuApi: MenuApi,
    private val datastore: DatastoreRepository,
    private val navigator: Navigator,
    savedState: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf<DishState?>(null)
        private set

    private val quantity = savedState.getLiveData("", 1)

    init {
        combine(
            dishRepository.getDish(dishId),
            quantity.asFlow()
        ) { dish, quantity ->
            state = if (state == null) {
                DishState(
                    dish = dish,
                    quantity = quantity
                )
            } else {
                state?.copy(
                    dish = dish,
                    quantity = quantity
                )
            }
        }
            .launchIn(viewModelScope)
    }

    fun dispatchEvent(event: DishEvent) {
        viewModelScope.launch {
            when (event) {
                DishEvent.AddToCart -> {
                    val quantity = state?.quantity ?: return@launch
                    cartRepository.addToCart(dishId, quantity)
                }
                DishEvent.AddPiece -> {
                    quantity.value = quantity.value?.plus(1)
                }
                DishEvent.RemovePiece -> {
                    quantity.value = quantity.value?.minus(1)?.coerceAtLeast(1)
                }
                DishEvent.ToggleFavorite -> {
                    toggleFavorite()
                }
                DishEvent.AddReview -> {
                    addReview()
                }
                DishEvent.BackClick -> {
                    navigator.back()
                }
                DishEvent.DismissAlert -> {
                    state = state?.copy(alertMessage = null)
                }
            }
        }
    }

    private fun addReview() {

    }

    private suspend fun toggleFavorite() {
        val currentIsFavorite = favoriteRepository.isFavorite(dishId)
        if (currentIsFavorite) {
            favoriteRepository.deleteFromFavorite(dishId)
        } else {
            favoriteRepository.addToFavorite(dishId)
        }
        if (datastore.isAuthorized()) {
            try {
                menuApi.changeFavorite(mapOf(dishId to !currentIsFavorite))
            } catch (exception: Exception) {
                Log.e(TAG, "Can't change favorite status: $exception")
            }
        }
    }

    companion object {
        private const val TAG = "DishViewModel"
    }
}

internal class DishViewModelFactory @AssistedInject constructor(
    @Assisted private val dishId: String,
    @Assisted owner: SavedStateRegistryOwner,
    @Assisted defaultArgs: Bundle? = null,
    private val dishRepository: DishRepository,
    private val favoriteRepository: FavoriteRepository,
    private val cartRepository: CartRepository,
    private val menuApi: MenuApi,
    private val datastore: DatastoreRepository,
    private val navigator: Navigator
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(DishViewModel::class.java)) {
            return DishViewModel(
                dishId = dishId,
                dishRepository = dishRepository,
                favoriteRepository = favoriteRepository,
                cartRepository = cartRepository,
                menuApi = menuApi,
                datastore = datastore,
                navigator = navigator,
                savedState = handle
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@AssistedFactory
internal interface DishViewModelAssistedFactory {
    fun create(
        dishId: String,
        owner: SavedStateRegistryOwner,
        defaultArgs: Bundle? = null
    ): DishViewModelFactory
}