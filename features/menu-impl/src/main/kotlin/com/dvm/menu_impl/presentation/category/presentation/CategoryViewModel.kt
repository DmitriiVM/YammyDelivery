package com.dvm.menu_impl.presentation.category.presentation

import android.os.Bundle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.dvm.cart_api.domain.CartInteractor
import com.dvm.cart_api.domain.model.CartItem
import com.dvm.menu_api.domain.CategoryInteractor
import com.dvm.menu_api.domain.DishInteractor
import com.dvm.menu_api.domain.model.OrderType
import com.dvm.menu_impl.presentation.category.presentation.model.CategoryData
import com.dvm.menu_impl.presentation.category.presentation.model.CategoryEvent
import com.dvm.menu_impl.presentation.category.presentation.model.CategoryState
import com.dvm.menu_impl.presentation.common.MENU_SPECIAL_OFFER
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.utils.Text
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.dvm.ui.R as CoreR

internal class CategoryViewModel(
    private val categoryInteractor: CategoryInteractor,
    private val dishInteractor: DishInteractor,
    private val cartInteractor: CartInteractor,
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
                        title = Text.Resource(CoreR.string.menu_item_special_offer),
                        categoryId = categoryId,
                        subcategories = emptyList(),
                        selectedId = null,
                        orderType = orderType
                    )
                }
                else -> {
                    val subcategories = categoryInteractor.getSubcategories(categoryId)
                    // TODO figure out why passing nullable optional arguments doesn't work
                    val selectedId = if (subcategoryId != null && subcategoryId != "null") {
                        subcategoryId
                    } else {
                        subcategories.firstOrNull()?.id ?: categoryId
                    }
                    val title = categoryInteractor.getCategoryTitle(categoryId)
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
                        val dishes = dishInteractor.getSpecialOffers()
                        flowOf(category.copy(dishes = dishes))
                    }
                    else -> {
                        val selectedId = requireNotNull(category.selectedId)
                        dishInteractor
                            .getDishes(selectedId)
                            .map { category.copy(dishes = it) }
                    }
                }
            }
            .onEach { category ->
                state = state.copy(
                    title = category.title,
                    subcategories = category.subcategories,
                    dishes = categoryInteractor.order(category.dishes, category.orderType),
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
                    cartInteractor.addToCart(
                        CartItem(
                            dishId = event.dishId,
                            quantity = 1
                        )
                    )
                    state = state.copy(
                        alert = Text.Resource(
                            resId = CoreR.string.message_dish_added_to_cart,
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
}