package com.dvm.menu.menu.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.menu.menu.domain.MenuInteractor
import com.dvm.menu.menu.domain.model.MenuItem
import com.dvm.menu.menu.presentation.model.MenuEvent
import com.dvm.menu.menu.presentation.model.MenuNavigationEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MenuViewModel(
    private val interactor: MenuInteractor = MenuInteractor()
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