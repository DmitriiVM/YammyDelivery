package com.dvm.order.order

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dvm.db.db_api.data.CartRepository
import com.dvm.navigation.Navigator
import com.dvm.network.network_api.api.CartApi
import com.dvm.order.order.model.OrderEvent
import com.dvm.order.order.model.OrderState
import com.dvm.preferences.datastore_api.data.DatastoreRepository
import com.dvm.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class OrderViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val cartApi: CartApi,
    private val datastore: DatastoreRepository,
    private val stringProvider: StringProvider,
    private val navigator: Navigator,
    savedState: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(OrderState())
        private set

    fun dispatchEvent(event: OrderEvent){
        when (event) {
            OrderEvent.BackClick -> {

            }
            OrderEvent.DismissAlert -> {

            }
        }
    }
}