package com.dvm.menu.category.presentation.model

sealed class Title {
    class Text(val value: String) : Title()
    class Resource(val value: Int) : Title()
}