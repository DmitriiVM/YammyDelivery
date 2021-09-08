package com.dvm.db.impl.repositories

import com.dvm.db.api.ProfileRepository
import com.dvm.db.api.models.Profile
import com.dvm.db.impl.dao.ProfileDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class DefaultProfileRepository(
    private val profileDao: ProfileDao
) : ProfileRepository {

    override fun profile(): Flow<Profile?> = profileDao.profile()

    override suspend fun updateProfile(profile: Profile) =
        withContext(Dispatchers.IO) {
            profileDao.updateProfile(profile)
        }

    override suspend fun deleteProfile() =
        withContext(Dispatchers.IO){
            profileDao.deleteProfile()
        }
}