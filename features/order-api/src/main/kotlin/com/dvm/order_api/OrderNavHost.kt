package com.dvm.order_api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface OrderNavHost {
    fun addComposables(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    )
}