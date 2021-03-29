package com.dvm.dish

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dvm.db.db_api.data.CartRepository
import com.dvm.db.db_api.data.DishRepository
import com.dvm.db.db_api.data.FavoriteRepository
import com.dvm.dish.model.DishEvent
import com.dvm.dish.model.DishNavigationEvent
import com.dvm.dish.model.DishState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class DishViewModel(
    private val dishId: String,
    private val dishRepository: DishRepository,
    private val favoriteRepository: FavoriteRepository,
    private val cartRepository: CartRepository
): ViewModel() {

    var state by mutableStateOf<DishState?>(null)
        private set

    private val _navigationEvent = MutableSharedFlow<DishNavigationEvent>()
    val navigationEvent: SharedFlow<DishNavigationEvent>
        get() = _navigationEvent

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
    
    fun dispatchEvent(event: DishEvent){
        viewModelScope.launch {
            when (event) {
                DishEvent.AddToCart -> { }
                DishEvent.AddPiece -> {
                    var quantity = state?.quantity ?: return@launch
                    state = state?.copy(quantity = ++quantity)
                }
                DishEvent.RemovePiece -> {
                    var quantity = state?.quantity ?: return@launch
                    state = state?.copy(quantity = (--quantity).coerceAtLeast(1))
                }
                DishEvent.ChangeFavorite -> { }
                DishEvent.AddReview -> { }
                DishEvent.NavigateUp -> {
                    _navigationEvent.emit(DishNavigationEvent.Up)
                }
            }
        }
    }
}

class DishViewModelFactory @AssistedInject constructor(
    @Assisted private val dishId: String,
    private val dishRepository: DishRepository,
    private val favoriteRepository: FavoriteRepository,
    private val cartRepository: CartRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DishViewModel::class.java)) {
            return DishViewModel(
                dishId = dishId,
                dishRepository = dishRepository,
                favoriteRepository = favoriteRepository,
                cartRepository = cartRepository,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@AssistedFactory
interface DishViewModelAssistedFactory {
    fun create(dishId: String): DishViewModelFactory
}