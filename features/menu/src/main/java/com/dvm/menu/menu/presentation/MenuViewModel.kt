package com.dvm.menu.menu.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dvm.menu.menu.domain.MenuInteractor
import com.dvm.menu.menu.domain.model.MenuItem
import com.dvm.menu.menu.presentation.model.MenuEvent
import com.dvm.menu.menu.presentation.model.MenuNavigationEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class MenuViewModel(
    private val interactor: MenuInteractor
) : ViewModel() {

    var menuItems by mutableStateOf(emptyList<MenuItem>())
        private set

    private val _navigationEvent = MutableSharedFlow<MenuNavigationEvent>()
    val navigationEvent: SharedFlow<MenuNavigationEvent>
        get() = _navigationEvent

    init {
        viewModelScope.launch {
            menuItems = interactor.getParentCategories()
        }
    }

    fun dispatch(event: MenuEvent) {
        viewModelScope.launch {
            _navigationEvent.emit(
                when (event) {
                    is MenuEvent.MenuItemClick -> MenuNavigationEvent.NavigateToCategory(event.id)
                    MenuEvent.AppMenuClick -> MenuNavigationEvent.OpenAppMenu
                    MenuEvent.SearchClick -> MenuNavigationEvent.NavigateToSearch
                }
            )
        }
    }
}

class MenuViewModelFactory @Inject constructor(
    private val interactor: MenuInteractor
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuViewModel::class.java)) {
            return MenuViewModel(interactor) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}