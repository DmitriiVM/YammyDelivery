package com.dvm.dish.presentation

import android.annotation.SuppressLint
import android.content.Context
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
import com.dvm.db.api.models.CartItem
import com.dvm.dish.R
import com.dvm.dish.presentation.model.DishEvent
import com.dvm.dish.presentation.model.DishState
import com.dvm.navigation.Navigator
import com.dvm.network.api.MenuApi
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.utils.getErrorMessage
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
internal class DishViewModel(
    private val context: Context,
    private val dishId: String,
    private val favoriteRepository: FavoriteRepository,
    private val cartRepository: CartRepository,
    private val menuApi: MenuApi,
    private val datastore: DatastoreRepository,
    private val navigator: Navigator,
    dishRepository: DishRepository,
    savedState: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(DishState())
        private set

    private val quantity = savedState.getLiveData("quantity", 1)

    init {
        combine(
            dishRepository.dish(dishId),
            quantity.asFlow()
        ) { dish, quantity ->
            state = state.copy(
                dish = dish,
                quantity = quantity
            )
        }
            .launchIn(viewModelScope)
    }

    fun dispatchEvent(event: DishEvent) {
        viewModelScope.launch {
            when (event) {
                DishEvent.AddToCart -> {
                    cartRepository.addToCart(
                        CartItem(
                            dishId = dishId,
                            quantity = state.quantity
                        )
                    )
                }
                DishEvent.RemovePiece -> {
                    val newValue = quantity.value?.minus(1)
                    quantity.value = newValue?.coerceAtLeast(1)
                }
                DishEvent.AddPiece -> {
                    quantity.value = quantity.value?.plus(1)
                }
                DishEvent.ToggleFavorite -> {
                    toggleFavorite()
                }
                DishEvent.DismissAlert -> {
                    state = state.copy(alertMessage = null)
                }
                DishEvent.BackClick -> {
                    navigator.back()
                }
                DishEvent.AddReviewClick -> {
                    if (datastore.isAuthorized()){
                        state = state.copy(reviewDialog = true)
                    } else {
                        state = state.copy(
                            alertMessage = context.getString(
                                R.string.dish_alert_unauthorized_review
                            )
                        )
                    }
                }
                DishEvent.DismissReviewDialog -> {
                    state = state.copy(reviewDialog = false)
                }
                is DishEvent.AddReview -> {
                    addReview(
                        rating = event.rating,
                        text = event.text
                    )
                }
            }
        }
    }

    private suspend fun toggleFavorite() {

        val currentIsFavorite = favoriteRepository.isFavorite(dishId)
        if (currentIsFavorite) {
            favoriteRepository.deleteFromFavorite(dishId)
        } else {
            favoriteRepository.addToFavorite(dishId)
        }

        if (!datastore.isAuthorized()) return
        try {
            menuApi.changeFavorite(
                token = requireNotNull(datastore.getAccessToken()),
                favorites = mapOf(dishId to !currentIsFavorite)
            )
        } catch (exception: Exception) {
            Log.e(TAG, "Can't change favorite status: $exception")
        }

    }

    private fun addReview(
        rating: Int,
        text: String
    ) {
        viewModelScope.launch {
            state = state.copy(networkCall = true)

            try {
                Log.d("mmm", "DishViewModel :  addReview --  1")
                menuApi.addReview(
                    token = requireNotNull(datastore.getAccessToken()),
                    dishId = dishId,
                    rating = rating,
                    text = text,
                )
                state = state.copy(
                    networkCall = false,
                    reviewDialog = false,
                    alertMessage = context.getString(R.string.dish_review_result)
                )
            } catch (exception: Exception) {
                state = state.copy(
                    alertMessage = exception.getErrorMessage(context),
                    networkCall = false
                )
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
    @ApplicationContext private val context: Context,
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
                context = context,
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