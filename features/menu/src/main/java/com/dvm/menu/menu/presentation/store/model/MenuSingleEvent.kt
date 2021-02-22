package com.dvm.menu.menu.presentation.store.model

sealed class MenuSingleEvent {
    data class Error(val error: String): MenuSingleEvent()
}