package com.dvm.menu.category.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.dvm.database.api.CartRepository
import com.dvm.database.api.CategoryRepository
import com.dvm.database.api.DishRepository
import com.dvm.database.api.models.CardDishDetails
import com.dvm.database.api.models.CartItem
import com.dvm.menu.R
import com.dvm.menu.category.presentation.model.CategoryData
import com.dvm.menu.category.presentation.model.CategoryEvent
import com.dvm.menu.category.presentation.model.CategoryState
import com.dvm.menu.category.presentation.model.OrderType
import com.dvm.menu.common.MENU_SPECIAL_OFFER
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.utils.Text
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val dishRepository: DishRepository,
    private val cartRepository: CartRepository,
    private val navigator: Navigator,
    savedState: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(CategoryState())
        private set

    private val categoryId = savedState.getLiveData<String>(Destination.Category.CATEGORY_ID)
    private val subcategoryId = savedState.getLiveData<String?>(Destination.Category.SUBCATEGORY_ID, null)
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
                    CategoryData(
                        title = Text.Resource(R.string.menu_item_special_offer),
                        categoryId = categoryId,
                        subcategories = emptyList(),
                        selectedId = null,
                        orderType = orderType
                    )
                }
                else -> {
                    val subcategories = categoryRepository.getSubcategories(categoryId)
                    // TODO figure out why passing nullable optional arguments doesn't work
                    val selectedId = if (subcategoryId != null && subcategoryId != "null") {
                        subcategoryId
                    } else {
                        subcategories.firstOrNull()?.id
                            ?: categoryId
                    }
                    val title = categoryRepository.getCategoryTitle(categoryId)
                    CategoryData(
                        title = Text.Plain(title),
                        categoryId = categoryId,
                        subcategories = subcategories,
                        selectedId = selectedId,
                        orderType = orderType,
                    )
                }
            }
        }
            .flatMapLatest { category ->
                when (category.categoryId) {
                    MENU_SPECIAL_OFFER -> {
                        val dishes = dishRepository.getSpecialOffers()
                        flowOf(category.copy(dishes = dishes))
                    }
                    else -> {
                        val selectedId = requireNotNull(category.selectedId)
                        dishRepository
                            .getDishes(selectedId)
                            .map { category.copy(dishes = it) }
                    }
                }
            }
            .onEach { category ->
                state = state.copy(
                    title = category.title,
                    subcategories = category.subcategories,
                    dishes = category.dishes.order(category.orderType),
                    selectedId = category.selectedId,
                    orderType = category.orderType
                )
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
                    state = state.copy(
                        alert = Text.Resource(
                            resId = R.string.message_dish_added_to_cart,
                            formatArgs = listOf(event.name)
                        )
                    )
                }
            }
            is CategoryEvent.OpenDish -> {
                navigator.goTo(Destination.Dish(event.dishId))
            }
            is CategoryEvent.ChangeSubcategory -> {
                subcategoryId.value = event.id
            }
            is CategoryEvent.OrderBy -> {
                orderType.value = event.orderType
            }
            CategoryEvent.DismissAlert -> {
                state = state.copy(alert = null)
            }
            CategoryEvent.Back -> {
                navigator.back()
            }
        }
    }

    private fun List<CardDishDetails>.order(orderType: OrderType) =
        when (orderType) {
            OrderType.ALPHABET_ASC -> this.sortedBy { it.name }
            OrderType.ALPHABET_DESC -> this.sortedByDescending { it.name }
            OrderType.POPULARITY_ASC -> this.sortedBy { it.likes }
            OrderType.POPULARITY_DESC -> this.sortedByDescending { it.likes }
            OrderType.RATING_ASC -> this.sortedBy { it.rating }
            OrderType.RATING_DESC -> this.sortedByDescending { it.rating }
        }
}