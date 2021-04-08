package com.dvm.dish.dish_impl

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.db.db_api.data.CartRepository
import com.dvm.db.db_api.data.DishRepository
import com.dvm.db.db_api.data.FavoriteRepository
import com.dvm.dish.R
import com.dvm.dish.presentation.model.DishEvent
import com.dvm.dish.presentation.model.DishState
import com.dvm.navigation.Destination
import com.dvm.navigation.Navigator
import com.dvm.network.network_api.api.MenuApi
import com.dvm.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DishViewModel @Inject constructor(
    private val dishRepository: DishRepository,
    private val favoriteRepository: FavoriteRepository,
    private val cartRepository: CartRepository,
    private val menuApi: MenuApi,
    private val stringProvider: StringProvider,
    private val navigator: Navigator,
    private val savedState: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf<DishState?>(null)
        private set

    private val dishId = requireNotNull(savedState.get<String>("dishId"))

    init {
        viewModelScope.launch {
            val dish = dishRepository.getDish(dishId)
            val hasSpecialOffer = dish.oldPrice > dish.price
            state = DishState(
                dish = dish,
                hasSpecialOffer = hasSpecialOffer
            )
        }
    }

    fun dispatchEvent(event: DishEvent) {
        viewModelScope.launch {
            when (event) {
                DishEvent.AddToCart -> {
                }
                DishEvent.AddPiece -> {
                    var quantity = state?.quantity ?: return@launch
                    state = state?.copy(quantity = ++quantity)
                }
                DishEvent.RemovePiece -> {
                    var quantity = state?.quantity ?: return@launch
                    state = state?.copy(quantity = (--quantity).coerceAtLeast(1))
                }
                DishEvent.ChangeFavorite -> {
                    if (favoriteRepository.isFavorite(dishId)) {
                        favoriteRepository.deleteFromFavorite(dishId)
                        try {
                            menuApi.changeFavorite(dishId, false)
                        } catch (exception: Exception) {  // TODO
                            favoriteRepository.addToFavorite(dishId)
                            state = state?.copy(
                                alertMessage = stringProvider.getString(R.string.add_favorite_error)
                            )
                        }
                    } else {
                        favoriteRepository.addToFavorite(dishId)
                        try {
                            menuApi.changeFavorite(dishId, true)
                        } catch (exception: Exception) { // TODO
                            favoriteRepository.deleteFromFavorite(dishId)
                            state = state?.copy(
                                alertMessage = stringProvider.getString(R.string.delete_favorite_error)
                            )
                        }
                    }
                }
                DishEvent.AddReview -> {
                }
                DishEvent.NavigateUp -> {
                    navigator.navigationTo?.invoke(Destination.Back)
                }
                DishEvent.DismissAlert -> {
                    state = state?.copy(alertMessage = null)
                }
            }
        }
    }
}