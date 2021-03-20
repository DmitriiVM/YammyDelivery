package com.dvm.menu.category.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dvm.db.dao.CartDao
import com.dvm.db.dao.CategoryDao
import com.dvm.db.dao.DishDao
import com.dvm.db.dao.FavoriteDao
import com.dvm.menu.Graph
import com.dvm.menu.category.presentation.model.CategoryEvent
import com.dvm.menu.category.presentation.model.CategoryNavigationEvent
import com.dvm.menu.category.presentation.model.CategoryState
import com.dvm.menu.category.presentation.model.SortType
import com.dvm.menu.common.MENU_SPECIAL_OFFER
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryId: String,
    private val categoryDao: CategoryDao,
    private val dishDao: DishDao,
    private val favoriteDao: FavoriteDao,
    private val cartDao: CartDao,
) : ViewModel() {

    var state by mutableStateOf(CategoryState())
        private set

    private val _navigationEvent = MutableSharedFlow<CategoryNavigationEvent>()
    val navigationEvent: SharedFlow<CategoryNavigationEvent>
        get() = _navigationEvent

    init {
        loadContent()
    }

    private fun loadContent() {
        viewModelScope.launch {
            when (categoryId) {
                MENU_SPECIAL_OFFER -> {
                    val dishes = dishDao.getSpecialOffers()
                    state = CategoryState(
                        title = "Акции",
                        dishes = dishes
                    )
                }
                else -> {
                    val subcategories = categoryDao.getChildCategories(categoryId)
                    val subcategoryId = subcategories.firstOrNull()?.id
                    val dishes = dishDao.getDishes(subcategoryId ?: categoryId)
                    val title = categoryDao.getCategoryTitle(categoryId)
                    state = CategoryState(
                        title = title,
                        subcategories = subcategories,
                        dishes = dishes,
                        selectedCategoryId = subcategoryId
                    )
                }
            }
        }
    }

    fun dispatch(event: CategoryEvent) {
        viewModelScope.launch {
            when (event) {
                is CategoryEvent.AddToCartClick -> {
                    /*cartDao.addToCart(action.dishId)*/
                }
                is CategoryEvent.DishClick -> {
                    _navigationEvent.emit(CategoryNavigationEvent.ToDetails(event.dishId))
                }
                is CategoryEvent.AddToFavoriteClick -> {
                    /*favoriteDao.addToFavorite(action.dishId)*/
                }
                CategoryEvent.NavigateUpClick -> {
                    _navigationEvent.emit(CategoryNavigationEvent.Up)
                }
                is CategoryEvent.SubcategoryClick -> {
                    val dishes = dishDao.getDishes(event.id)
                    state = state.copy(
                        dishes = dishes,
                        selectedCategoryId = event.id
                    )
                }
                is CategoryEvent.SortPick -> {
                    val dishes = state.dishes
                    val sortedDishes = when (event.sortType) {
                        SortType.ALPHABET_ASC -> dishes.sortedBy { it.name }
                        SortType.ALPHABET_DESC -> dishes.sortedByDescending { it.name }
                        SortType.POPULARITY_ASC -> dishes.sortedBy { it.likes }
                        SortType.POPULARITY_DESC -> dishes.sortedByDescending { it.likes }
                        SortType.RATING_ASC -> dishes.sortedBy { it.rating }
                        SortType.RATING_DESC -> dishes.sortedByDescending { it.rating }
                    }
                    state = state.copy(dishes = sortedDishes, selectedSort = event.sortType)
                }
            }
        }
    }
}

class CategoryViewModelFactory(
    private val categoryId: String,
    private val categoryDao: CategoryDao = Graph.categoryDao,
    private val dishDao: DishDao = Graph.dishDao,
    private val favoriteDao: FavoriteDao = Graph.favoriteDao,
    private val cartDao: CartDao = Graph.cartDao,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(
                categoryId = categoryId,
                categoryDao = categoryDao,
                dishDao = dishDao,
                favoriteDao = favoriteDao,
                cartDao = cartDao,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}




