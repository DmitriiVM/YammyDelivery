package com.dvm.menu_api

import androidx.navigation.NavGraphBuilder

interface MenuNavHost {
    fun addComposables(navGraphBuilder: NavGraphBuilder)
}