package com.dvm.network.api

import com.dvm.network.api.response.ProfileResponse

interface ProfileApi {

    suspend fun getProfile(token: String): ProfileResponse

    suspend fun editProfile(
        token: String,
        firstName: String,
        lastName: String,
        email: String
    ): ProfileResponse

    suspend fun changePassword(
        token: String,
        oldPassword: String,
        newPassword: String
    )
}