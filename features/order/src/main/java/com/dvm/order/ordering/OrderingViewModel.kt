package com.dvm.order.ordering

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.navigation.Navigator
import com.dvm.network.network_api.api.OrderApi
import com.dvm.order.ordering.model.OrderingEvent
import com.dvm.order.ordering.model.OrderingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class OrderingViewModel @Inject constructor(
    private val orderApi: OrderApi,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(OrderingState())
        private set

    fun dispatchEvent(event: OrderingEvent) {
        when (event) {
            is OrderingEvent.MakeOrder -> {
                state = state.copy(networkCall = true)
                viewModelScope.launch {
                    try {
                        val order = orderApi.createOrder(
                            address = event.fields.address,
                            entrance = event.fields.entrance.toIntOrNull(),
                            floor = event.fields.floor.toIntOrNull(),
                            apartment = event.fields.apartment,
                            intercom = event.fields.intercom,
                            comment = event.fields.comment,
                        )

                        Log.d("mmm", "OrderingViewModel :  dispatchEvent --  $order")

                    } catch (exception: Exception) {
                        state = state.copy(
                            networkCall = false,
                            alertMessage = exception.message
                        )
                    }
                }
            }
            OrderingEvent.MapButtonClick -> {

            }
            OrderingEvent.BackClick -> {
                navigator.back()
            }
            OrderingEvent.DismissAlert -> {
                state = state.copy(alertMessage = null)
            }
        }
    }
}