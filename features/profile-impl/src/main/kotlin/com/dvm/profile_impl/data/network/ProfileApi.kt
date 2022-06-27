package com.dvm.profile_impl.data.network

import com.dvm.profile_api.domain.model.Profile
import com.dvm.profile_impl.data.mappers.toProfile
import com.dvm.profile_impl.data.network.request.ChangePasswordRequest
import com.dvm.profile_impl.data.network.request.EditProfileRequest
import com.dvm.profile_impl.data.network.response.ProfileResponse
import com.dvm.profile_impl.domain.ProfileApi
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders

internal class DefaultProfileApi(
    private val client: HttpClient
) : ProfileApi {

    override suspend fun getProfile(token: String): Profile =
        client.get<ProfileResponse>("profile") {
            header(HttpHeaders.Authorization, token)
        }
            .toProfile()

    override suspend fun editProfile(
        token: String,
        firstName: String,
        lastName: String,
        email: String
    ): Profile =
        client.put<ProfileResponse>("profile") {
            header(HttpHeaders.Authorization, token)
            body = EditProfileRequest(
                firstName = firstName,
                lastName = lastName,
                email = email
            )
        }
            .toProfile()


    override suspend fun changePassword(
        token: String,
        oldPassword: String,
        newPassword: String
    ): Unit =
        client.put("profile/password") {
            header(HttpHeaders.Authorization, token)
            body = ChangePasswordRequest(
                oldPassword = oldPassword,
                newPassword = newPassword,
            )
        }
}