package com.dvm.menu.category.presentation.model

import com.dvm.menu.R

internal enum class OrderType(val stringResource: Int){
    ALPHABET_ASC(R.string.category_order_alphabet_asc),
    ALPHABET_DESC(R.string.category_order_alphabet_desc),
    POPULARITY_ASC(R.string.category_order_popularity_asc),
    POPULARITY_DESC(R.string.category_order_popularity_desc),
    RATING_ASC(R.string.category_order_rating_asc),
    RATING_DESC(R.string.category_order_rating_desc),
}