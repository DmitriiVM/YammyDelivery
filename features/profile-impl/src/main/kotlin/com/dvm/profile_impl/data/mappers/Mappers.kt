package com.dvm.profile_impl.data.mappers

import com.dvm.profile_api.domain.model.Profile
import com.dvm.profile_impl.data.network.response.ProfileResponse
import com.dvm.profiledatabase.ProfileEntity

fun ProfileResponse.toProfile() =
    Profile(
        firstName = firstName,
        lastName = lastName,
        email = email,
    )

fun ProfileEntity.toProfile() =
    Profile(
        firstName = firstName,
        lastName = lastName,
        email = email,
    )

fun Profile.toProfileEntity() =
    ProfileEntity(
        firstName = firstName,
        lastName = lastName,
        email = email,
    )