package com.dvm.profile_impl.domain

import com.dvm.preferences.api.DatastoreRepository
import com.dvm.profile_api.domain.ProfileInteractor
import com.dvm.profile_api.domain.model.Profile
import kotlinx.coroutines.flow.Flow

internal class DefaultProfileInteractor(
    private val repository: ProfileRepository,
    private val api: ProfileApi,
    private val datastore: DatastoreRepository
) : ProfileInteractor {

    override fun profile(): Flow<Profile?> =
        repository
            .profile()

    override suspend fun updateProfileData(profile: Profile) {
        val editedProfile = api.editProfile(
            token = getToken(),
            firstName = profile.firstName,
            lastName = profile.lastName,
            email = profile.email,
        )
        repository.updateProfile(
            profile = editedProfile
        )
    }

    override suspend fun deleteProfile() {
        repository.deleteProfile()
    }

    override suspend fun updateProfile() {
        val token = datastore.getAccessToken() ?: return
        val profile = api.getProfile(token)
        repository.updateProfile(
            profile = profile
        )
    }

    override suspend fun changePassword(
        oldPassword: String,
        newPassword: String
    ) {
        api.changePassword(
            token = getToken(),
            oldPassword = oldPassword,
            newPassword = newPassword
        )
    }

    override fun authorized(): Flow<Boolean> =
        datastore.authorized()

    private suspend fun getToken() =
        requireNotNull(datastore.getAccessToken())
}