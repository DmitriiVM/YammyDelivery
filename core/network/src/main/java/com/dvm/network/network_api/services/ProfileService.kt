package com.dvm.network.network_api.services

import com.dvm.network.network_api.response.ProfileResponse

interface ProfileService {

    suspend fun getProfile(): ProfileResponse

    suspend fun editProfile(
        firstName: String,
        lastName: String,
        email: String
    ): ProfileResponse

    suspend fun changePassword(
        oldPassword: String,
        newPassword: String
    )
}