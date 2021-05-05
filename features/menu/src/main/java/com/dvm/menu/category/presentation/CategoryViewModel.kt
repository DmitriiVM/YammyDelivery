package com.dvm.menu.category.presentation

import android.os.Bundle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.dvm.db.db_api.data.CartRepository
import com.dvm.db.db_api.data.CategoryRepository
import com.dvm.db.db_api.data.DishRepository
import com.dvm.db.db_api.data.FavoriteRepository
import com.dvm.db.db_api.data.models.CategoryDish
import com.dvm.menu.R
import com.dvm.menu.category.presentation.model.CategoryEvent
import com.dvm.menu.category.presentation.model.CategoryState
import com.dvm.menu.category.presentation.model.OrderType
import com.dvm.menu.common.MENU_SPECIAL_OFFER
import com.dvm.navigation.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.network.network_api.api.MenuApi
import com.dvm.preferences.datastore_api.data.DatastoreRepository
import com.dvm.utils.StringProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

internal class CategoryViewModel(
    private val categoryId: String,
    private val categoryRepository: CategoryRepository,
    private val dishRepository: DishRepository,
    private val favoriteRepository: FavoriteRepository,
    private val cartRepository: CartRepository,
    private val datastore: DatastoreRepository,
    private val menuApi: MenuApi,
    private val stringProvider: StringProvider,
    private val navigator: Navigator,
    private val savedState: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(CategoryState())
        private set

    private val subcategoryId = savedState.get<String>("subcategoryId")

    init {
        loadContent()
    }

    private fun loadContent() {
        viewModelScope.launch {
            when (categoryId) {
                MENU_SPECIAL_OFFER -> {
                    val dishes = dishRepository.getSpecialOffers()
                    state = CategoryState(
                        title = stringProvider.getString(R.string.menu_item_special_offer),
                        dishes = dishes
                    )
                }
                else -> {
                    val subcategories = categoryRepository.getSubcategories(categoryId)
                    val subcategoryId = subcategoryId ?: subcategories.firstOrNull()?.id
                    var dishes = dishRepository.getDishes(subcategoryId ?: categoryId)
                    savedState.get<Int>("orderType")?.let {
                        dishes = dishes.order(OrderType.values()[it])
                    }
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
                    if (datastore.isAuthorized()) {
                        /*cartRepository.addToCart(action.dishId)*/
                    } else {
                        false
                    }
                }
                is CategoryEvent.DishClick -> {
                    navigator.goTo(Destination.Dish(event.dishId))
                }
                CategoryEvent.BackClick -> {
                    navigator.back()
                }
                is CategoryEvent.ChangeSubcategory -> {
                    val subcategoryId = event.id
                    val dishes = dishRepository.getDishes(subcategoryId)
                    state = state.copy(
                        dishes = dishes,
                        selectedCategoryId = subcategoryId
                    )
                    savedState.set("subcategoryId", subcategoryId)
                }
                is CategoryEvent.OrderBy -> {
                    val orderType = event.orderType
                    val dishes = state.dishes.order(orderType)
                    state = state.copy(dishes = dishes, selectedOrder = orderType)
                    savedState.set("orderType", orderType.ordinal)
                }
                CategoryEvent.DismissAlert -> {
                    state = state.copy(alertMessage = null)
                }
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

internal class CategoryViewModelFactory @AssistedInject constructor(
    @Assisted private val categoryId: String,
    @Assisted owner: SavedStateRegistryOwner,
    @Assisted defaultArgs: Bundle? = null,
    private val navigator: Navigator,
    private val categoryRepository: CategoryRepository,
    private val dishRepository: DishRepository,
    private val favoriteRepository: FavoriteRepository,
    private val cartRepository: CartRepository,
    private val datastore: DatastoreRepository,
    private val menuApi: MenuApi,
    private val stringProvider: StringProvider,
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(
                categoryId = categoryId,
                categoryRepository = categoryRepository,
                dishRepository = dishRepository,
                favoriteRepository = favoriteRepository,
                cartRepository = cartRepository,
                datastore = datastore,
                menuApi = menuApi,
                stringProvider = stringProvider,
                navigator = navigator,
                savedState = handle
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@AssistedFactory
internal interface CategoryViewModelAssistedFactory {
    fun create(
        categoryId: String,
        owner: SavedStateRegistryOwner,
        defaultArgs: Bundle? = null
    ): CategoryViewModelFactory
}

