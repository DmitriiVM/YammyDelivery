package com.dvm.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.dvm.cart.model.CartEvent
import com.dvm.cart.model.CartState
import com.dvm.db.api.data.CartRepository
import com.dvm.navigation.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.network.api.api.CartApi
import com.dvm.preferences.api.data.DatastoreRepository
import com.dvm.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val cartApi: CartApi,
    private val datastore: DatastoreRepository,
    private val stringProvider: StringProvider,
    private val navigator: Navigator,
    savedState: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(CartState())
        private set

    private val promoCode = savedState.getLiveData("cart_promocode", "")
    private val promoCodeText = savedState.getLiveData("cart_promocode_description", "")
    private val appliedPromoCode = savedState.getLiveData("cart_promocode_applied", false)

    init {
        combine(
            cartRepository.cartItems(),
            promoCode.asFlow(),
            promoCodeText.asFlow(),
            appliedPromoCode.asFlow()
        ) { items, promoCode, promoCodeDescription, appliedPromoCode ->
            val totalPrice = items.sumBy { it.price * it.quantity }
            state = state.copy(
                items = items,
                totalPrice = totalPrice,
                promoCode = promoCode,
                promoCodeText = promoCodeDescription,
                appliedPromoCode = appliedPromoCode,
            )
        }
            .launchIn(viewModelScope)
    }

    fun dispatchEvent(event: CartEvent) {
        when (event) {
            is CartEvent.DishClick -> {
                navigator.goTo(Destination.Dish(event.dishId))
            }
            is CartEvent.PromoCodeTextChanged -> {
                promoCode.value = event.text
            }
            is CartEvent.AddPiece -> {
                state.items.firstOrNull { it.dishId == event.dishId }?.quantity?.let { quantity ->
                    viewModelScope.launch {
                        cartRepository.addToCart(
                            dishId = event.dishId,
                            quantity = quantity + 1
                        )
                    }
                }
            }
            is CartEvent.RemovePiece -> {
                state.items.firstOrNull { it.dishId == event.dishId }?.quantity?.let { quantity ->
                    viewModelScope.launch {
                        val newQuantity = quantity - 1
                        if (newQuantity > 0) {
                            cartRepository.addToCart(
                                dishId = event.dishId,
                                quantity = newQuantity
                            )
                        } else {
                            cartRepository.removeItem(event.dishId)
                        }
                    }
                }
            }
            CartEvent.ApplyPromoCode -> {
                updateCart(state.promoCode) { promocode, promocodeText ->
                    if (promocode == "promocode"){
                        promoCodeText.value = promocodeText
                        appliedPromoCode.value = true
                    } else {
                        state = state.copy(alertMessage = stringProvider.getString(R.string.cart_message_promocode_fail))
                    }
                }
            }
            CartEvent.CancelPromoCode -> {
                updateCart("") { _,_ ->
                    promoCode.value = ""
                    promoCodeText.value = ""
                    appliedPromoCode.value = false
                }
            }
            CartEvent.CreateOrder -> {
                updateCart(state.promoCode) { _,_ ->
                    navigator.goTo(Destination.Orders)
                }
            }
            CartEvent.DismissAlert -> {
                state = state.copy(alertMessage = null)
            }
            CartEvent.BackClick -> {
                navigator.back()
            }
        }
    }

    private fun updateCart(promoCode: String, onUpdated: (String, String) -> Unit) {
        viewModelScope.launch {
            if (datastore.isAuthorized()) {
                try {
                    state = state.copy(networkCall = true)
                    val cart = cartApi.updateCart(
                        promocode = promoCode,
                        items = state.items.associate { it.dishId to it.quantity }
                    )
                    onUpdated(cart.promocode, cart.promotext)
                } catch (exception: Exception) {
                    state = state.copy(alertMessage = exception.message)
                } finally {
                    state = state.copy(networkCall = false)
                }
            } else {
                navigator.goTo(Destination.Login)
            }
        }
    }
}