package com.dvm.menu_impl.presentation.menu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.menu_api.domain.CategoryInteractor
import com.dvm.menu_api.domain.DishInteractor
import com.dvm.menu_impl.presentation.menu.model.MenuEvent
import com.dvm.menu_impl.presentation.menu.model.MenuItem
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import kotlinx.coroutines.launch

@OptIn(ExperimentalStdlibApi::class)
internal class MenuViewModel(
    private val categoryInteractor: CategoryInteractor,
    private val dishInteractor: DishInteractor,
    private val navigator: Navigator
) : ViewModel() {

    var menuItems by mutableStateOf(emptyList<MenuItem>())
        private set

    init {
        viewModelScope.launch {
            val items = categoryInteractor
                .getParentCategories()
                .map { category ->
                    MenuItem.Item(
                        id = category.id,
                        title = category.name,
                        imageUrl = category.icon
                    )
                }

            menuItems = buildList {
                if (dishInteractor.hasSpecialOffers()) {
                    add(MenuItem.SpecialOffer)
                }
                addAll(items)
            }
        }
    }

    fun dispatch(event: MenuEvent) {
        when (event) {
            is MenuEvent.OpenMenuItem -> {
                navigator.goTo(Destination.Category(event.id))
            }
            MenuEvent.Search -> {
                navigator.goTo(Destination.Search)
            }
        }
    }
}