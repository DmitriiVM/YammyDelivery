package com.dvm.profile_api.domain

import com.dvm.profile_api.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileInteractor {
    fun profile(): Flow<Profile?>
    fun authorized(): Flow<Boolean>

    suspend fun deleteProfile()
    suspend fun updateProfile()
    suspend fun updateProfileData(profile: Profile)

    suspend fun changePassword(
        oldPassword: String,
        newPassword: String
    )
}