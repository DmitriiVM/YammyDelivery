package com.dvm.profile_impl.domain

import com.dvm.profile_api.domain.model.Profile
import kotlinx.coroutines.flow.Flow

internal interface ProfileRepository {
    fun profile(): Flow<Profile?>
    suspend fun updateProfile(profile: Profile)
    suspend fun deleteProfile()
}