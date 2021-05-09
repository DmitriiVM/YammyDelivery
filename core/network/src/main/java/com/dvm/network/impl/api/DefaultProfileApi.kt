package com.dvm.network.impl.api

import com.dvm.network.api.ProfileApi
import com.dvm.network.api.response.ProfileResponse
import com.dvm.network.impl.ApiService
import com.dvm.network.impl.request.ChangePasswordRequest
import com.dvm.network.impl.request.EdieProfileRequest
import javax.inject.Inject

internal class DefaultProfileApi @Inject constructor(
    private val apiService: ApiService
) : ProfileApi {

    override suspend fun getProfile(
        token: String
    ): ProfileResponse =
        apiService.getProfile(token)

    override suspend fun editProfile(
        token: String,
        firstName: String,
        lastName: String,
        email: String
    ): ProfileResponse =
        apiService.editProfile(
            token = token,
            editProfileRequest = EdieProfileRequest(
                firstName = firstName,
                lastName = lastName,
                email = email
            )
        )


    override suspend fun changePassword(
        token: String,
        oldPassword: String,
        newPassword: String
    ) {
        apiService.changePassword(
            token = token,
            changePasswordRequest = ChangePasswordRequest(
                oldPassword = oldPassword,
                newPassword = newPassword,
            )
        )
    }
}