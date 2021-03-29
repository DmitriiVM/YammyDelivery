package com.dvm.db.db_impl.di

import android.content.Context
import com.dvm.module_injector.BaseDependencies

interface DatabaseDependencies: BaseDependencies {
    fun context(): Context
}