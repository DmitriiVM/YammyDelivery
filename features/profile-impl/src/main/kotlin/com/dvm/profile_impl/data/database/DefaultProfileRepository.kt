package com.dvm.profile_impl.data.database

import com.dvm.profile_api.domain.model.Profile
import com.dvm.profile_impl.data.mappers.toProfile
import com.dvm.profile_impl.data.mappers.toProfileEntity
import com.dvm.profile_impl.domain.ProfileRepository
import com.dvm.profiledatabase.ProfileQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class DefaultProfileRepository(
    private val profileQueries: ProfileQueries
) : ProfileRepository {

    override fun profile(): Flow<Profile?> =
        profileQueries
            .profile()
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { it?.toProfile() }

    override suspend fun updateProfile(profile: Profile) =
        withContext(Dispatchers.IO) {
            profileQueries.transaction {
                profileQueries.delete()
                profileQueries.insert(profile.toProfileEntity())
            }
        }

    override suspend fun deleteProfile() =
        withContext(Dispatchers.IO) {
            profileQueries.delete()
        }
}