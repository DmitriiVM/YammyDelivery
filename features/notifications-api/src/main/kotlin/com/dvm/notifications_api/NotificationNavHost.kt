package com.dvm.notifications_api

import androidx.navigation.NavGraphBuilder

interface NotificationNavHost {

    fun addComposables(navGraphBuilder: NavGraphBuilder)
}

const val NOTIFICATION_URI = "app://com.dvm.yammydelivery"