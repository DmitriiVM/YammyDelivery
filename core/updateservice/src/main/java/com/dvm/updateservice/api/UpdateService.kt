package com.dvm.updateservice.api

interface UpdateService {
    suspend fun updateAll()
    suspend fun updateOrders()
    suspend fun syncFavorites()
}