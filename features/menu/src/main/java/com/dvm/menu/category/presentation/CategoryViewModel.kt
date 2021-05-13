package com.dvm.menu.category.presentation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.dvm.db.api.CartRepository
import com.dvm.db.api.CategoryRepository
import com.dvm.db.api.DishRepository
import com.dvm.db.api.models.CartItem
import com.dvm.db.api.models.CategoryDish
import com.dvm.menu.R
import com.dvm.menu.category.presentation.model.CategoryEvent
import com.dvm.menu.category.presentation.model.CategoryState
import com.dvm.menu.category.presentation.model.OrderType
import com.dvm.menu.common.MENU_SPECIAL_OFFER
import com.dvm.navigation.Navigator
import com.dvm.navigation.api.model.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
internal class CategoryViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val categoryRepository: CategoryRepository,
    private val dishRepository: DishRepository,
    private val cartRepository: CartRepository,
    private val navigator: Navigator,
    savedState: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(CategoryState())
        private set

    private val categoryId = savedState.getLiveData<String>("categoryId")
    private val subcategoryId = savedState.getLiveData<String?>("subcategoryId", null)
    private val orderType = savedState.getLiveData("orderType", OrderType.ALPHABET_ASC)

    init {
        combine(
            categoryId.asFlow(),
            subcategoryId.asFlow()
                .distinctUntilChanged(),
            orderType.asFlow()
                .distinctUntilChanged()
        ) { categoryId, subcategoryId, orderType ->
            when (categoryId) {
                MENU_SPECIAL_OFFER -> {
                    state = CategoryState(
                        title = context.getString(R.string.menu_item_special_offer),
                        dishes = dishRepository.getSpecialOffers().order(orderType)
                    )
                }
                else -> {
                    val subcategories = categoryRepository.getSubcategories(categoryId)
                    val selectedId = subcategoryId ?: subcategories.firstOrNull()?.id ?: categoryId
                    val dishes = dishRepository.getDishes(selectedId)
                    val title = categoryRepository.getCategoryTitle(categoryId)
                    state = CategoryState(
                        title = title,
                        subcategories = subcategories,
                        dishes = dishes.order(orderType),
                        selectedCategoryId = selectedId
                    )
                }
            }
        }
            .launchIn(viewModelScope)
    }

    fun dispatch(event: CategoryEvent) {
        when (event) {
            is CategoryEvent.AddToCart -> {
                viewModelScope.launch {
                    cartRepository.addToCart(
                        CartItem(
                            dishId = event.dishId,
                            quantity = 1
                        )
                    )
                }
            }
            is CategoryEvent.DishClick -> {
                navigator.goTo(Destination.Dish(event.dishId))
            }
            is CategoryEvent.ChangeSubcategory -> {
                subcategoryId.value = event.id
            }
            is CategoryEvent.OrderBy -> {
                orderType.value = event.orderType
            }
            CategoryEvent.DismissAlert -> {
                state = state.copy(alertMessage = null)
            }
            CategoryEvent.BackClick -> {
                navigator.back()
            }
        }
    }

    private fun List<CategoryDish>.order(orderType: OrderType) =
        when (orderType) {
            OrderType.ALPHABET_ASC -> this.sortedBy { it.name }
            OrderType.ALPHABET_DESC -> this.sortedByDescending { it.name }
            OrderType.POPULARITY_ASC -> this.sortedBy { it.likes }
            OrderType.POPULARITY_DESC -> this.sortedByDescending { it.likes }
            OrderType.RATING_ASC -> this.sortedBy { it.rating }
            OrderType.RATING_DESC -> this.sortedByDescending { it.rating }
        }
}