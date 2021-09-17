package com.dvm.cart

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.dvm.cart.model.CartEvent
import com.dvm.cart.model.CartState
import com.dvm.database.api.CartRepository
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.network.api.CartApi
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.utils.getErrorMessage
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
internal class CartViewModel(
    private val context: Context,
    private val cartRepository: CartRepository,
    private val cartApi: CartApi,
    private val datastore: DatastoreRepository,
    private val navigator: Navigator,
    private val savedState: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(CartState())
        private set

    private val promoCode = savedState.getLiveData("cart_promocode", "")
    private val promoCodeText = savedState.getLiveData("cart_promocode_description", "")
    private val appliedPromoCode = savedState.getLiveData("cart_promocode_applied", false)

    private var resumeOrderAfterLogin: Boolean
        get() = savedState.get("resume_order") ?: false
        set(value) = savedState.set("resume_order", value)

    init {
        combine(
            cartRepository.cartItems(),
            promoCode.asFlow(),
            promoCodeText.asFlow(),
            appliedPromoCode.asFlow()
        ) { items, promoCode, promoCodeText, appliedPromoCode ->
            val totalPrice = items.sumOf { it.price * it.quantity }
            state = state.copy(
                items = items,
                totalPrice = totalPrice,
                promoCode = promoCode,
                promoCodeText = promoCodeText,
                appliedPromoCode = appliedPromoCode,
            )
        }
            .launchIn(viewModelScope)
    }

    fun onViewAppeared() {
        if (resumeOrderAfterLogin) {
            makeOrder()
            resumeOrderAfterLogin = false
        }
    }

    fun dispatch(event: CartEvent) {
        when (event) {
            is CartEvent.OpenDish -> {
                navigator.goTo(Destination.Dish(event.dishId))
            }
            is CartEvent.ChangePromoCode -> {
                promoCode.value = event.text
            }
            is CartEvent.AddPiece -> {
                viewModelScope.launch {
                    cartRepository.addPiece(event.dishId)
                }
            }
            is CartEvent.RemovePiece -> {
                viewModelScope.launch {
                    cartRepository.removePiece(event.dishId)
                }
            }
            CartEvent.ApplyPromoCode -> {
                updateCart(state.promoCode) { promocode, promocodeText ->
                    if (promocode == "promocode") {
                        promoCodeText.value = promocodeText
                        appliedPromoCode.value = true
                    } else {
                        state =
                            state.copy(alert = context.getString(R.string.cart_message_promocode_fail))
                    }
                }
            }
            CartEvent.CancelPromoCode -> {
                updateCart("") { _, _ ->
                    promoCode.value = ""
                    promoCodeText.value = ""
                    appliedPromoCode.value = false
                }
            }
            CartEvent.CreateOrder -> {
                makeOrder()
            }
            CartEvent.DismissAlert -> {
                state = state.copy(alert = null)
            }
            CartEvent.Back -> {
                navigator.back()
            }
        }
    }

    private fun makeOrder() {
        updateCart(state.promoCode) { _, _ ->
            navigator.goTo(Destination.Ordering)
        }
    }

    private fun updateCart(promoCode: String, onUpdated: (String, String) -> Unit) {
        viewModelScope.launch {
            if (datastore.isAuthorized()) {
                try {
                    state = state.copy(progress = true)
                    val cart = cartApi.updateCart(
                        token = requireNotNull(datastore.getAccessToken()),
                        promocode = promoCode,
                        items = state.items.associate { it.dishId to it.quantity }
                    )
                    state = state.copy(progress = false)
                    onUpdated(cart.promocode, cart.promotext)
                } catch (exception: Exception) {
                    state = state.copy(
                        alert = exception.getErrorMessage(context),
                        progress = false
                    )
                }
            } else {
                resumeOrderAfterLogin = true
                navigator.goTo(Destination.Login())
            }
        }
    }
}