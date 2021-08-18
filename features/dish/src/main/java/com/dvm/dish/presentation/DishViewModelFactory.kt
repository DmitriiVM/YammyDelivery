package com.dvm.dish.presentation

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.dvm.db.api.CartRepository
import com.dvm.db.api.DishRepository
import com.dvm.db.api.FavoriteRepository
import com.dvm.navigation.api.Navigator
import com.dvm.network.api.MenuApi
import com.dvm.preferences.api.DatastoreRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext

internal class DishViewModelFactory @AssistedInject constructor(
    @Assisted private val dishId: String,
    @Assisted owner: SavedStateRegistryOwner,
    @Assisted defaultArgs: Bundle? = null,
    @ApplicationContext private val context: Context,
    private val dishRepository: DishRepository,
    private val favoriteRepository: FavoriteRepository,
    private val cartRepository: CartRepository,
    private val menuApi: MenuApi,
    private val datastore: DatastoreRepository,
    private val navigator: Navigator
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(DishViewModel::class.java)) {
            return DishViewModel(
                context = context,
                dishId = dishId,
                dishRepository = dishRepository,
                favoriteRepository = favoriteRepository,
                cartRepository = cartRepository,
                menuApi = menuApi,
                datastore = datastore,
                navigator = navigator,
                savedState = handle
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@AssistedFactory
internal interface DishViewModelAssistedFactory {
    fun create(
        dishId: String,
        owner: SavedStateRegistryOwner,
        defaultArgs: Bundle? = null
    ): DishViewModelFactory
}