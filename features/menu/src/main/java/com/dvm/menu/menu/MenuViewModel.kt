package com.dvm.menu.menu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.db.api.CategoryRepository
import com.dvm.db.api.DishRepository
import com.dvm.menu.menu.model.MenuEvent
import com.dvm.menu.menu.model.MenuItem
import com.dvm.navigation.Navigator
import com.dvm.navigation.api.model.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MenuViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val dishRepository: DishRepository,
    private val navigator: Navigator
) : ViewModel() {

    var menuItems by mutableStateOf(emptyList<MenuItem>())
        private set

    init {
        viewModelScope.launch {
            val items = categoryRepository
                .getParentCategories()
                .map { category ->
                    MenuItem.Item(
                        id = category.id,
                        title = category.name,
                        imageUrl = category.icon
                    )
                }

            menuItems = mutableListOf<MenuItem>().apply {
                if (dishRepository.hasSpecialOffers()) {
                    add(MenuItem.SpecialOffer)
                }
                addAll(items)
            }
        }
    }

    fun dispatch(event: MenuEvent) {
        when (event) {
            is MenuEvent.MenuItemClick -> {
                navigator.goTo(Destination.Category(event.id))
            }
            MenuEvent.SearchClick -> {
                navigator.goTo(Destination.Search)
            }
        }
    }
}