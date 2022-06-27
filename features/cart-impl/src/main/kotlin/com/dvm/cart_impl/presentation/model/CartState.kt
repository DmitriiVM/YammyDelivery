package com.dvm.cart_impl.presentation.model

import androidx.compose.runtime.Immutable
import com.dvm.cart_api.domain.model.CartDishItem

@Immutable
internal data class CartState(
    val items: List<CartDishItem> = emptyList(),
    val totalPrice: Int = 0,
    val promoCode: String = "",
    val promoCodeText: String = "",
    val appliedPromoCode: Boolean = false,
    val progress: Boolean = false,
    val alert: Int? = null
)