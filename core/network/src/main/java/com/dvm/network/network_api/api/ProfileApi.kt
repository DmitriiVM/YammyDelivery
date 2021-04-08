package com.dvm.network.network_api.api

import com.dvm.network.network_api.response.ProfileResponse

interface ProfileApi {

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