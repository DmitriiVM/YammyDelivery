package com.dvm.db.db_impl.data

import com.dvm.db.db_api.data.CartRepository
import com.dvm.db.db_impl.AppDatabase
import javax.inject.Inject

internal class DefaultCartRepository @Inject constructor(
    private val database: AppDatabase
) : CartRepository