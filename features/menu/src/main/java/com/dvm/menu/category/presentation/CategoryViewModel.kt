package com.dvm.menu.category.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.db.AppDatabase
import com.dvm.menu.category.domain.CategoryInteractor
import com.dvm.menu.category.temp.CategoryIntent
import com.dvm.menu.category.temp.CategoryState
import com.dvm.menu.category.temp.SortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// TODO id in constructor
class CategoryViewModel(app: Application) : AndroidViewModel(app) {  // TODO

    private val _state = MutableStateFlow<CategoryState>(CategoryState.Loading)
    val state: StateFlow<CategoryState>
        get() = _state

    private val interactor = CategoryInteractor(  // TODO
        AppDatabase.getDb(app.applicationContext).categoryDao(),
        AppDatabase.getDb(app.applicationContext).dishDao()
    )

    fun loadContent(categoryId: String) {  // TODO on init
        viewModelScope.launch(Dispatchers.IO) {
            val subcategories = interactor.getSubcategories(categoryId)
            val dishes = interactor.getDishes(subcategories.firstOrNull()?.id ?: categoryId)
            _state.value = CategoryState.Data(subcategories = subcategories, dishes = dishes)
        }
    }

    fun dispatch(intent: CategoryIntent) {
        val currentState = _state.value
        when (intent) {

            is CategoryIntent.AddToCartClick -> { }

            is CategoryIntent.DishClick -> { }

            is CategoryIntent.SubcategoryClick -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (currentState is CategoryState.Data){
                        val dishes = interactor.getDishes(intent.id)
                        _state.value = currentState.copy(dishes = dishes)
                    }
                }
            }

            is CategoryIntent.SortPick -> {
                if (currentState is CategoryState.Data){
                    val dishes = currentState.dishes
                    val sortedDishes = when (intent.type){
                        SortType.ALPHABET_ASC -> dishes.sortedBy { it.name }
                        SortType.ALPHABET_DESC -> dishes.sortedByDescending { it.name }
                        SortType.POPULARITY_ASC -> dishes.sortedBy { it.likes }
                        SortType.POPULARITY_DESC -> dishes.sortedByDescending { it.likes }
                        SortType.RATING_ASC -> dishes.sortedBy { it.rating }
                        SortType.RATING_DESC -> dishes.sortedByDescending { it.rating }
                    }
                    _state.value = currentState.copy(dishes = sortedDishes, showSort = false)
                }
            }

            is CategoryIntent.SortClick -> {
                if (currentState is CategoryState.Data) {
                    _state.value = currentState.copy(showSort = intent.isShown)
                }
            }

            CategoryIntent.NavigateUpClick -> { }
        }
    }

}