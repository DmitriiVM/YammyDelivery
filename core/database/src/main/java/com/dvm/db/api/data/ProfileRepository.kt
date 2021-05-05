package com.dvm.db.api.data

import com.dvm.db.api.data.models.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun profile(): Flow<Profile>
    suspend fun updateProfile(profile: Profile)
}