package com.dvm.order_impl.data.network

import com.dvm.network.util.getAllChunks
import com.dvm.order_api.domain.model.OrderDetails
import com.dvm.order_api.domain.model.OrderStatus
import com.dvm.order_impl.data.mappers.toOrderDetails
import com.dvm.order_impl.data.mappers.toOrderStatus
import com.dvm.order_impl.data.network.request.CancelOrderRequest
import com.dvm.order_impl.data.network.request.CreateOrderRequest
import com.dvm.order_impl.data.network.response.OrderResponse
import com.dvm.order_impl.data.network.response.StatusResponse
import com.dvm.order_impl.domain.OrderApi
import com.dvm.utils.AppException
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlin.coroutines.cancellation.CancellationException

internal class DefaultOrderApi(
    private val client: HttpClient
) : OrderApi {

    override suspend fun createOrder(
        token: String,
        address: String,
        entrance: Int?,
        floor: Int?,
        apartment: String?,
        intercom: String?,
        comment: String?,
    ): OrderDetails =
        client.post<OrderResponse>("orders/new") {
            header(HttpHeaders.Authorization, token)
            body = CreateOrderRequest(
                address = address,
                entrance = entrance,
                floor = floor,
                apartment = apartment,
                intercom = intercom,
                comment = comment,
            )
        }
            .toOrderDetails()

    override suspend fun getOrders(
        token: String,
        lastUpdateTime: Long?
    ): List<OrderDetails> = getAllChunks { offset, limit ->
        client.get<List<OrderResponse>>("orders") {
            header(HttpHeaders.Authorization, token)
            header(HttpHeaders.IfModifiedSince, lastUpdateTime)
            parameter(HEADER_OFFSET, offset)
            parameter(HEADER_LIMIT, limit)
        }
            .map { it.toOrderDetails() }
    }

    override suspend fun getStatuses(
        lastUpdateTime: Long?
    ): List<OrderStatus> =
        try {
            client.get<List<StatusResponse>>("orders/statuses") {
                header(HttpHeaders.IfModifiedSince, lastUpdateTime)
            }
                .map { it.toOrderStatus() }
        } catch (exception: CancellationException) {
            throw CancellationException()
        } catch (exception: Exception) {
            if (exception is AppException.NotModifiedException) {
                emptyList()
            } else {
                throw exception
            }
        }

    override suspend fun cancelOrder(
        token: String,
        orderId: String
    ): OrderDetails =
        client.put<OrderResponse>("orders/cancel") {
            header(HttpHeaders.Authorization, token)
            body = CancelOrderRequest(orderId)
        }
            .toOrderDetails()

    companion object {
        private const val HEADER_OFFSET = "offset"
        private const val HEADER_LIMIT = "limit"
    }
}