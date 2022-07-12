package com.dvm.profile_impl.data.network

import com.dvm.profile_api.domain.model.Profile
import com.dvm.profile_impl.data.mappers.toProfile
import com.dvm.profile_impl.data.network.request.ChangePasswordRequest
import com.dvm.profile_impl.data.network.request.EditProfileRequest
import com.dvm.profile_impl.data.network.response.ProfileResponse
import com.dvm.profile_impl.domain.ProfileApi
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

internal class DefaultProfileApi(
    private val client: HttpClient
) : ProfileApi {

    override suspend fun getProfile(token: String): Profile =
        client.get("profile") {
            header(HttpHeaders.Authorization, token)
        }
            .body<ProfileResponse>()
            .toProfile()

    override suspend fun editProfile(
        token: String,
        firstName: String,
        lastName: String,
        email: String
    ): Profile =
        client.put("profile") {
            header(HttpHeaders.Authorization, token)
            setBody(
                EditProfileRequest(
                    firstName = firstName,
                    lastName = lastName,
                    email = email
                )
            )
        }
            .body<ProfileResponse>()
            .toProfile()


    override suspend fun changePassword(
        token: String,
        oldPassword: String,
        newPassword: String
    ) {
        client.put("profile/password") {
            header(HttpHeaders.Authorization, token)
            setBody(
                ChangePasswordRequest(
                    oldPassword = oldPassword,
                    newPassword = newPassword,
                )
            )
        }
    }

}