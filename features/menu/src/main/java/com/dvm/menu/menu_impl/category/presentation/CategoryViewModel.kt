package com.dvm.menu.menu_impl.category.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dvm.db.db_api.data.CartRepository
import com.dvm.db.db_api.data.CategoryRepository
import com.dvm.db.db_api.data.DishRepository
import com.dvm.db.db_api.data.FavoriteRepository
import com.dvm.menu.menu_impl.category.presentation.model.CategoryEvent
import com.dvm.menu.menu_impl.category.presentation.model.CategoryNavigationEvent
import com.dvm.menu.menu_impl.category.presentation.model.CategoryState
import com.dvm.menu.menu_impl.category.presentation.model.SortType
import com.dvm.menu.menu_impl.common.MENU_SPECIAL_OFFER
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

internal class CategoryViewModel(
    private val categoryId: String,
    private val categoryRepository: CategoryRepository,
    private val dishRepository: DishRepository,
    private val favoriteRepository: FavoriteRepository,
    private val cartRepository: CartRepository,
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
                    val dishes = dishRepository.getSpecialOffers()
                    state = CategoryState(
                        title = "Акции",
                        dishes = dishes
                    )
                }
                else -> {
                    val subcategories = categoryRepository.getChildCategories(categoryId)
                    val subcategoryId = subcategories.firstOrNull()?.id
                    val dishes = dishRepository.getDishes(subcategoryId ?: categoryId)
                    val title = categoryRepository.getCategoryTitle(categoryId)
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
                is CategoryEvent.AddToCart -> {
                    /*cartRepository.addToCart(action.dishId)*/
                }
                is CategoryEvent.NavigateToDish -> {
                    _navigationEvent.emit(CategoryNavigationEvent.ToDetails(event.dishId))
                }
                is CategoryEvent.AddToFavorite -> {
                    /*favoriteRepository.addToFavorite(action.dishId)*/
                }
                CategoryEvent.NavigateUp -> {
                    _navigationEvent.emit(CategoryNavigationEvent.Up)
                }
                is CategoryEvent.NavigateToSubcategory -> {
                    val dishes = dishRepository.getDishes(event.id)
                    state = state.copy(
                        dishes = dishes,
                        selectedCategoryId = event.id
                    )
                }
                is CategoryEvent.Sort -> {
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

internal class CategoryViewModelFactory @AssistedInject constructor(
    @Assisted private val categoryId: String,
    private val categoryRepository: CategoryRepository,
    private val dishRepository: DishRepository,
    private val favoriteRepository: FavoriteRepository,
    private val cartRepository: CartRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(
                categoryId = categoryId,
                categoryRepository = categoryRepository,
                dishRepository = dishRepository,
                favoriteRepository = favoriteRepository,
                cartRepository = cartRepository,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@AssistedFactory
internal interface CategoryViewModelAssistedFactory {
    fun create(categoryId: String): CategoryViewModelFactory
}



