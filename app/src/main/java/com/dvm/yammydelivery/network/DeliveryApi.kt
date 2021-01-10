package com.dvm.yammydelivery.network

import com.dvm.yammydelivery.model.Dish
import retrofit2.http.GET

interface DeliveryApi {

    @GET("dishes?offset=0&limit=10")
    fun getDishes(): Dish

}