package com.dvm.database.impl.repositories

import com.dvm.database.Profile
import com.dvm.database.ProfileQueries
import com.dvm.database.api.ProfileRepository
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class DefaultProfileRepository(
    private val profileQueries: ProfileQueries
) : ProfileRepository {

    override fun profile(): Flow<Profile?> =
        profileQueries
            .profile()
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)

    override suspend fun updateProfile(profile: Profile) =
        withContext(Dispatchers.IO) {
            profileQueries.transaction {
                profileQueries.delete()
                profileQueries.insert(profile)
            }
        }

    override suspend fun deleteProfile() =
        withContext(Dispatchers.IO) {
            profileQueries.delete()
        }
}