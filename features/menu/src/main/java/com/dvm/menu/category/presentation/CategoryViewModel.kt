package com.dvm.menu.category.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.dvm.database.CartItem
import com.dvm.database.api.CartRepository
import com.dvm.database.api.CategoryRepository
import com.dvm.database.api.DishRepository
import com.dvm.database.api.models.CardDish
import com.dvm.menu.R
import com.dvm.menu.category.presentation.model.CategoryData
import com.dvm.menu.category.presentation.model.CategoryEvent
import com.dvm.menu.category.presentation.model.CategoryState
import com.dvm.menu.category.presentation.model.OrderType
import com.dvm.menu.common.MENU_SPECIAL_OFFER
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
internal class CategoryViewModel(
    private val context: Context,
    private val categoryRepository: CategoryRepository,
    private val dishRepository: DishRepository,
    private val cartRepository: CartRepository,
    private val navigator: Navigator,
    arguments: Bundle?,
    savedState: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(CategoryState())
        private set

    private val categoryId = savedState.getLiveData<String>(
        Destination.Category.CATEGORY_ID,
        arguments?.getString(Destination.Category.CATEGORY_ID)
    )
    private val subcategoryId = savedState.getLiveData<String?>(
        Destination.Category.SUBCATEGORY_ID,
        arguments?.getString(Destination.Category.SUBCATEGORY_ID)
    )
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
                        title = context.getString(R.string.menu_item_special_offer),
                        categoryId = categoryId,
                        subcategories = emptyList(),
                        selectedId = null,
                        orderType = orderType
                    )
                }
                else -> {
                    val subcategories = categoryRepository.getSubcategories(categoryId)
                    // TODO figure out why passing of nullable optional arguments doesn't work
                    val selectedId = if (subcategoryId != null && subcategoryId != "null") {
                        subcategoryId
                    } else {
                        subcategories.firstOrNull()?.id
                            ?: categoryId
                    }
                    val title = categoryRepository.getCategoryTitle(categoryId)
                    CategoryData(
                        title = title,
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
                        alert = String.format(
                            context.getString(
                                R.string.message_dish_added_to_cart,
                                event.name
                            )
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

    private fun List<CardDish>.order(orderType: OrderType) =
        when (orderType) {
            OrderType.ALPHABET_ASC -> this.sortedBy { it.name }
            OrderType.ALPHABET_DESC -> this.sortedByDescending { it.name }
            OrderType.POPULARITY_ASC -> this.sortedBy { it.likes }
            OrderType.POPULARITY_DESC -> this.sortedByDescending { it.likes }
            OrderType.RATING_ASC -> this.sortedBy { it.rating }
            OrderType.RATING_DESC -> this.sortedByDescending { it.rating }
        }
}