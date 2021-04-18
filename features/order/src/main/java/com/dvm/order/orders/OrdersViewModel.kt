package com.dvm.order.orders

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dvm.db.db_api.data.CartRepository
import com.dvm.navigation.Navigator
import com.dvm.network.network_api.api.CartApi
import com.dvm.order.orders.model.OrdersEvent
import com.dvm.order.orders.model.OrdersState
import com.dvm.preferences.datastore_api.data.DatastoreRepository
import com.dvm.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class OrdersViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val cartApi: CartApi,
    private val datastore: DatastoreRepository,
    private val stringProvider: StringProvider,
    private val navigator: Navigator,
    savedState: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(OrdersState())
        private set

    fun dispatchEvent(event: OrdersEvent){
        when (event) {
            OrdersEvent.BackClick -> {

            }
            OrdersEvent.DismissAlert -> {

            }
        }
    }
}