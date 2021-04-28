package com.dvm.db.db_impl.data

import com.dvm.db.db_api.data.ProfileRepository
import com.dvm.db.db_api.data.models.Profile
import com.dvm.db.db_impl.data.dao.ProfileDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultProfileRepository @Inject constructor(
    private val profileDao: ProfileDao
) : ProfileRepository {

    override fun profile(): Flow<Profile> = profileDao.profile()

    override suspend fun updateProfile(profile: Profile) = withContext(Dispatchers.IO) {
        profileDao.deleteProfile()
        profileDao.insertProfile(profile)
    }
}