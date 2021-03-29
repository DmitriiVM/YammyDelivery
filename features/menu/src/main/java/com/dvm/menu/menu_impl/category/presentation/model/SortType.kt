package com.dvm.menu.menu_impl.category.presentation.model

internal enum class SortType(val title: String){
    ALPHABET_ASC("По алфавиту ▲"),
    ALPHABET_DESC("По алфавиту ▼"),
    POPULARITY_ASC("По популярности ▲"),
    POPULARITY_DESC("По популярности ▼"),
    RATING_ASC("По рейтингу ▲"),
    RATING_DESC("По рейтингу ▼"),
}