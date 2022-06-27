package com.dvm.menu_api.domain.model

import com.dvm.ui.R as CoreR

enum class OrderType(val text: Int, val sign: Int) {
    ALPHABET_ASC(
        text = CoreR.string.category_order_alphabet_asc,
        sign = CoreR.string.category_order_up_sign
    ),
    ALPHABET_DESC(
        text = CoreR.string.category_order_alphabet_desc,
        sign = CoreR.string.category_order_down_sign
    ),
    POPULARITY_ASC(
        text = CoreR.string.category_order_popularity_asc,
        sign = CoreR.string.category_order_up_sign
    ),
    POPULARITY_DESC(
        text = CoreR.string.category_order_popularity_desc,
        sign = CoreR.string.category_order_down_sign
    ),
    RATING_ASC(
        text = CoreR.string.category_order_rating_asc,
        sign = CoreR.string.category_order_up_sign
    ),
    RATING_DESC(
        text = CoreR.string.category_order_rating_desc,
        sign = CoreR.string.category_order_down_sign
    ),
}