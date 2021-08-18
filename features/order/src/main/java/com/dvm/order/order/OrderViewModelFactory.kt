package com.dvm.order.order

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.dvm.db.api.CartRepository
import com.dvm.db.api.OrderRepository
import com.dvm.navigation.api.Navigator
import com.dvm.network.api.OrderApi
import com.dvm.preferences.api.DatastoreRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext

internal class OrderViewModelFactory @AssistedInject constructor(
    @ApplicationContext private val context: Context,
    @Assisted private val orderId: String,
    @Assisted owner: SavedStateRegistryOwner,
    @Assisted defaultArgs: Bundle? = null,
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val orderApi: OrderApi,
    private val datastore: DatastoreRepository,
    private val navigator: Navigator,
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OrderViewModel(
                orderId = orderId,
                context = context,
                orderRepository = orderRepository,
                cartRepository = cartRepository,
                orderApi = orderApi,
                datastore = datastore,
                navigator = navigator
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@AssistedFactory
internal interface OrderViewModelAssistedFactory {
    fun create(
        orderId: String,
        owner: SavedStateRegistryOwner,
        defaultArgs: Bundle? = null
    ): OrderViewModelFactory
}