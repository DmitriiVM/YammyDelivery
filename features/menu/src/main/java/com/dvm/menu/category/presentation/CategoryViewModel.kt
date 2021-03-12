package com.dvm.menu.category.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dvm.menu.Graph
import com.dvm.menu.category.domain.CategoryInteractor
import com.dvm.menu.category.temp.CategoryAction
import com.dvm.menu.category.temp.CategoryState
import com.dvm.menu.category.temp.SortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryId: String,
    private val interactor: CategoryInteractor = Graph.interactor
) : ViewModel() {

    var state by mutableStateOf<CategoryState>(CategoryState.Loading)
        private set

    fun loadContent() {
        viewModelScope.launch(Dispatchers.IO) {
            val subcategories = interactor.getSubcategories(categoryId)
            val subcategoryId = subcategories.firstOrNull()?.id
            val dishes = interactor.getDishes(subcategoryId ?: categoryId)
            val title = interactor.getCategoryTitle(categoryId)
            state = CategoryState.Data(
                title = title,
                subcategories = subcategories,
                dishes = dishes,
                selectedCategoryId = subcategoryId
            )
        }
    }

    fun dispatch(action: CategoryAction) {
        val currentState = state
        when (action) {

            is CategoryAction.AddToCartClick -> {
            }

            is CategoryAction.DishClick -> {
            }

            is CategoryAction.SubcategoryClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (currentState is CategoryState.Data) {
                        val dishes = interactor.getDishes(action.id)
                        state = currentState.copy(
                            dishes = dishes,
                            selectedCategoryId = action.id
                        )
                    }
                }
            }

            is CategoryAction.SortPick -> {
                if (currentState is CategoryState.Data) {
                    val dishes = currentState.dishes
                    val sortedDishes = when (action.type) {
                        SortType.ALPHABET_ASC -> dishes.sortedBy { it.name }
                        SortType.ALPHABET_DESC -> dishes.sortedByDescending { it.name }
                        SortType.POPULARITY_ASC -> dishes.sortedBy { it.likes }
                        SortType.POPULARITY_DESC -> dishes.sortedByDescending { it.likes }
                        SortType.RATING_ASC -> dishes.sortedBy { it.rating }
                        SortType.RATING_DESC -> dishes.sortedByDescending { it.rating }
                    }
                    state = currentState.copy(dishes = sortedDishes, selectedSort = action.type)
                }
            }

            CategoryAction.NavigateUpClick -> {
            }
        }
    }
}

class CategoryViewModelFactory(
    private val categoryId: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(categoryId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}




