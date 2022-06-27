package com.dvm.cart_impl

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dvm.cart_api.CartNavHost
import com.dvm.cart_impl.presentation.CartScreen
import com.dvm.navigation.api.model.Destination

internal class DefaultCartNavHost : CartNavHost {

    override fun addComposables(navGraphBuilder: NavGraphBuilder) {

        navGraphBuilder.composable(Destination.Cart.route) {
            CartScreen()
        }
    }
}