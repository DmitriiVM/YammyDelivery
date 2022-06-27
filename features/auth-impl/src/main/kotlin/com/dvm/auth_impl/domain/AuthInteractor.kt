package com.dvm.auth_impl.domain

import com.dvm.auth_impl.domain.model.AuthData
import com.dvm.auth_impl.domain.model.Token
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.profile_api.domain.ProfileInteractor
import com.dvm.profile_api.domain.model.Profile
import com.dvm.updateservice.api.UpdateService

internal interface AuthInteractor {
    suspend fun login(
        login: String,
        password: String
    ): AuthData

    suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): AuthData

    suspend fun sendEmail(email: String)

    suspend fun sendCode(
        email: String,
        code: String
    )

    suspend fun resetPassword(
        email: String,
        code: String,
        password: String
    )

    suspend fun refreshToken(
        refreshToken: String
    ): Token
}

internal class DefaultAuthInteractor(
    private val authApi: AuthApi,
    private val profileInteractor: ProfileInteractor,
    private val datastore: DatastoreRepository,
    private val updateService: UpdateService,
) : AuthInteractor {

    override suspend fun login(login: String, password: String): AuthData {
        val authData = authApi.login(login, password)
        datastore.saveAccessToken(authData.accessToken)
        datastore.saveRefreshToken(authData.refreshToken)
        profileInteractor.updateProfileData(
            Profile(
                firstName = authData.firstName,
                lastName = authData.lastName,
                email = authData.email
            )
        )
        updateService.syncFavorites()
        return authData
    }


    override suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): AuthData {
        val authData = authApi.register(
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password,
        )
        datastore.saveAccessToken(authData.accessToken)
        datastore.saveRefreshToken(authData.refreshToken)
        profileInteractor.updateProfileData(
            Profile(
                firstName = authData.firstName,
                lastName = authData.lastName,
                email = authData.email,
            )
        )
        updateService.syncFavorites()
        return authData
    }

    override suspend fun sendEmail(email: String) {
        authApi.sendEmail(email)
    }

    override suspend fun sendCode(email: String, code: String) {
        authApi.sendCode(email, code)
    }

    override suspend fun resetPassword(
        email: String,
        code: String,
        password: String
    ) {
        authApi.resetPassword(
            email = email,
            code = code,
            password = password
        )
    }

    override suspend fun refreshToken(refreshToken: String): Token =
        authApi.refreshToken(refreshToken)

}