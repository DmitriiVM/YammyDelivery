package com.dvm.network.network_impl.sevices

import com.dvm.network.network_api.response.OrderResponse
import com.dvm.network.network_api.response.StatusResponse
import com.dvm.network.network_api.services.OrderService
import com.dvm.network.network_impl.ApiService
import com.dvm.network.network_impl.request.CancelOrderRequest
import com.dvm.network.network_impl.request.CreateOrderRequest
import com.dvm.preferences.datastore_api.data.DatastoreRepository
import javax.inject.Inject

internal class DefaultOrderService @Inject constructor(
    private val apiService: ApiService,
    private val datastore: DatastoreRepository
) : OrderService {

    override suspend fun createOrder(
        address: String,
        entrance: Int,
        floor: Int,
        apartment: String,
        intercom: String,
        comment: String
    ): OrderResponse =
        apiService.createOrder(
            token = getAccessToken(),
            createOrderRequest = CreateOrderRequest(
                address = address,
                entrance = entrance,
                floor = floor,
                apartment = apartment,
                intercom = intercom,
                comment = comment,
            )
        )

    override suspend fun getOrders(): List<OrderResponse> =
        apiService.getOrders(getAccessToken())

    override suspend fun getStatuses(): List<StatusResponse>  =
        apiService.getStatuses()

    override suspend fun cancelOrder(orderId: Int): OrderResponse =
        apiService.cancelOrder(
            token = getAccessToken(),
            cancelOrderRequest = CancelOrderRequest(orderId)
        )

    private suspend fun getAccessToken() = requireNotNull(datastore.getAccessToken())
}