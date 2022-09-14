package com.dvm.auth_api

import androidx.navigation.NavGraphBuilder

interface AuthNavHost {
    fun addComposables(navGraphBuilder: NavGraphBuilder)
}