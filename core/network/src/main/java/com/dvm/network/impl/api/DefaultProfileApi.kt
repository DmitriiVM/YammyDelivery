package com.dvm.network.impl.api

import com.dvm.network.api.ProfileApi
import com.dvm.network.api.response.ProfileResponse
import com.dvm.network.impl.ApiService
import com.dvm.network.impl.request.ChangePasswordRequest
import com.dvm.network.impl.request.EdieProfileRequest
import com.dvm.preferences.api.DatastoreRepository
import javax.inject.Inject

internal class DefaultProfileApi @Inject constructor(
    private val apiService: ApiService,
    private val datastore: DatastoreRepository
) : ProfileApi {

    override suspend fun getProfile(): ProfileResponse =
        apiService.getProfile(getAccessToken())

    override suspend fun editProfile(
        firstName: String,
        lastName: String,
        email: String
    ): ProfileResponse =
        apiService.editProfile(
            token = getAccessToken(),
            editProfileRequest = EdieProfileRequest(
                firstName = firstName,
                lastName = lastName,
                email = email
            )
        )


    override suspend fun changePassword(
        oldPassword: String,
        newPassword: String
    ) {
        apiService.changePassword(
            token = getAccessToken(),
            changePasswordRequest = ChangePasswordRequest(
                oldPassword = oldPassword,
                newPassword = newPassword,
            )
        )
    }

    private suspend fun getAccessToken() = requireNotNull(datastore.getAccessToken())
}