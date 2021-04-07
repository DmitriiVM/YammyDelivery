package com.dvm.menu.category.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.db.db_api.data.CartRepository
import com.dvm.db.db_api.data.CategoryRepository
import com.dvm.db.db_api.data.DishRepository
import com.dvm.db.db_api.data.FavoriteRepository
import com.dvm.menu.category.presentation.model.CategoryEvent
import com.dvm.menu.category.presentation.model.CategoryState
import com.dvm.menu.category.presentation.model.SortType
import com.dvm.menu.common.MENU_SPECIAL_OFFER
import com.dvm.navigation.Destination
import com.dvm.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val dishRepository: DishRepository,
    private val favoriteRepository: FavoriteRepository,
    private val cartRepository: CartRepository,
    private val navigator: Navigator,
    savedState: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(CategoryState())
        private set

    private val categoryId = requireNotNull(savedState.get<String>("categoryId"))

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
                    navigator.navigationTo?.invoke(Destination.Dish(event.dishId))
                }
                is CategoryEvent.AddToFavorite -> {
                    /*favoriteRepository.addToFavorite(action.dishId)*/
                }
                CategoryEvent.NavigateUp -> {
                    navigator.navigationTo?.invoke(Destination.Back)
                }
                is CategoryEvent.ChangeSubcategory -> {
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


