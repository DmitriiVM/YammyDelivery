package com.dvm.db.db_impl.data

import com.dvm.db.db_api.data.FavoriteRepository
import com.dvm.db.db_impl.AppDatabase
import javax.inject.Inject

internal class DefaultFavoriteRepository @Inject constructor(
    private val database: AppDatabase
) : FavoriteRepository