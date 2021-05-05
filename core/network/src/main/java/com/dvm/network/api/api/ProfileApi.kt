package com.dvm.network.api.api

import com.dvm.network.api.response.ProfileResponse

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