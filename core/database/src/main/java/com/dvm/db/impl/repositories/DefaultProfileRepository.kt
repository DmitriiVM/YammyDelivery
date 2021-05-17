package com.dvm.db.impl.repositories

import com.dvm.db.api.ProfileRepository
import com.dvm.db.api.models.Profile
import com.dvm.db.impl.dao.ProfileDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultProfileRepository @Inject constructor(
    private val profileDao: ProfileDao
) : ProfileRepository {

    override fun profile(): Flow<Profile?> = profileDao.profile()

    override suspend fun updateProfile(profile: Profile) =
        withContext(Dispatchers.IO) {
            profileDao.updateCart(profile)
        }

    override suspend fun deleteProfile() =
        withContext(Dispatchers.IO){
            profileDao.deleteProfile()
        }
}