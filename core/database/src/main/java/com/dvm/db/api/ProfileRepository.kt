package com.dvm.db.api

import com.dvm.db.api.models.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun profile(): Flow<Profile>
    suspend fun updateProfile(profile: Profile)
}