package com.dvm.updateservice.api

interface UpdateService {
    suspend fun update()
    suspend fun updateOrders()
    suspend fun syncFavorites()
}