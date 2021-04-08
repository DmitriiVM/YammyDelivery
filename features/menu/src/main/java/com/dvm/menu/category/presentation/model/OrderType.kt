package com.dvm.menu.category.presentation.model

internal enum class OrderType(val title: String){
    ALPHABET_ASC("По алфавиту ▲"),
    ALPHABET_DESC("По алфавиту ▼"),
    POPULARITY_ASC("По популярности ▲"),
    POPULARITY_DESC("По популярности ▼"),
    RATING_ASC("По рейтингу ▲"),
    RATING_DESC("По рейтингу ▼"),
}