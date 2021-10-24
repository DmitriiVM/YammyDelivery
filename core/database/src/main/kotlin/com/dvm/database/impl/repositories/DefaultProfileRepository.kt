package com.dvm.database.impl.repositories

import com.dvm.database.api.ProfileRepository
import com.dvm.database.api.models.Profile
import com.dvm.database.impl.dao.ProfileDao
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class DefaultProfileRepository @Inject constructor(
    private val profileDao: ProfileDao
) : ProfileRepository {

    override fun profile(): Flow<Profile?> = profileDao.profile()

    override suspend fun updateProfile(profile: Profile) =
        withContext(Dispatchers.IO) {
            profileDao.updateProfile(profile)
        }

    override suspend fun deleteProfile() =
        withContext(Dispatchers.IO) {
            profileDao.deleteProfile()
        }
}