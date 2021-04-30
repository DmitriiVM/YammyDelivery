package com.dvm.cart.model

import androidx.compose.runtime.Immutable
import com.dvm.db.db_api.data.models.CartItemDetails

@Immutable
internal data class CartState(
    val items: List<CartItemDetails> = emptyList(),
    val totalPrice: Int = 0,
    val promoCode: String = "",
    val promoCodeText: String = "",
    val appliedPromoCode: Boolean = false,
    val networkCall: Boolean = false,
    val alertMessage: String? = null
)