package com.dvm.menu.category.presentation.model

import com.dvm.menu.R

internal enum class OrderType(val text: Int, val sign: Int) {
    ALPHABET_ASC(
        text = R.string.category_order_alphabet_asc,
        sign = R.string.category_order_up_sign
    ),
    ALPHABET_DESC(
        text = R.string.category_order_alphabet_desc,
        sign = R.string.category_order_down_sign
    ),
    POPULARITY_ASC(
        text = R.string.category_order_popularity_asc,
        sign = R.string.category_order_up_sign
    ),
    POPULARITY_DESC(
        text = R.string.category_order_popularity_desc,
        sign = R.string.category_order_down_sign
    ),
    RATING_ASC(
        text = R.string.category_order_rating_asc,
        sign = R.string.category_order_up_sign
    ),
    RATING_DESC(
        text = R.string.category_order_rating_desc,
        sign = R.string.category_order_down_sign
    ),
}