package com.dvm.auth.auth_impl.di

import com.dvm.module_injector.BaseDependencies

interface AuthDependencies: BaseDependencies {
    fun temp(): String
}