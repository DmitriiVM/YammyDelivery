package com.dvm.dish.dish_api

import com.dvm.module_injector.BaseAPI

interface DishApi : BaseAPI{
    fun dishLauncher(): DishLauncher
}