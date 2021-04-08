package com.dvm.network.network_impl.api

import com.dvm.network.network_api.api.ProfileApi
import com.dvm.network.network_api.response.ProfileResponse
import com.dvm.network.network_impl.ApiService
import com.dvm.network.network_impl.api
import com.dvm.network.network_impl.request.ChangePasswordRequest
import com.dvm.network.network_impl.request.EdieProfileRequest
import com.dvm.preferences.datastore_api.data.DatastoreRepository
import javax.inject.Inject

internal class DefaultProfileApi @Inject constructor(
    private val apiService: ApiService,
    private val datastore: DatastoreRepository
) : ProfileApi {

    override suspend fun getProfile(): ProfileResponse =
        api {
            apiService.getProfile(getAccessToken())
        }

    override suspend fun editProfile(
        firstName: String,
        lastName: String,
        email: String
    ): ProfileResponse =
        api {
            apiService.editProfile(
                token = getAccessToken(),
                editProfileRequest = EdieProfileRequest(
                    firstName = firstName,
                    lastName = lastName,
                    email = email
                )
            )
        }

    override suspend fun changePassword(
        oldPassword: String,
        newPassword: String
    ) =
        api {
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