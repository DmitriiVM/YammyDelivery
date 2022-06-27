package com.dvm.profile_impl.domain

import com.dvm.profile_api.domain.model.Profile

internal interface ProfileApi {
    suspend fun getProfile(token: String): Profile

    suspend fun editProfile(
        token: String,
        firstName: String,
        lastName: String,
        email: String
    ): Profile

    suspend fun changePassword(
        token: String,
        oldPassword: String,
        newPassword: String
    )
}