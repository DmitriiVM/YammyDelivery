package com.dvm.menu.menu.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.menu.menu.domain.MenuInteractor
import com.dvm.menu.menu.domain.model.MenuItem
import com.dvm.menu.menu.presentation.model.MenuEvent
import com.dvm.navigation.Navigator
import com.dvm.navigation.api.model.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MenuViewModel @Inject constructor(
    private val interactor: MenuInteractor,
    private val navigator: Navigator
) : ViewModel() {

    var menuItems by mutableStateOf(emptyList<MenuItem>())
        private set

    init {
        viewModelScope.launch {
            menuItems = interactor.getParentCategories()
        }
    }

    fun dispatch(event: MenuEvent) {
        viewModelScope.launch {
            when (event) {
                is MenuEvent.MenuItemClick -> {
                    navigator.goTo(Destination.Category(event.id))
                }
                MenuEvent.AppMenuClick -> {

                }
                MenuEvent.SearchClick -> {
                    navigator.goTo(Destination.Search)
                }
            }
        }
    }
}