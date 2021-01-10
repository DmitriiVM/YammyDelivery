package com.dvm.yammydelivery

import com.dvm.yammydelivery.model.Dish
import com.dvm.yammydelivery.network.DeliveryApiService

class ViewModel {


    fun getDishes(): Dish {


        return DeliveryApiService().api().getDishes()

    }

}