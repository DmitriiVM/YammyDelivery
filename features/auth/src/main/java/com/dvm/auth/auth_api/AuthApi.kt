package com.dvm.auth.auth_api

import com.dvm.module_injector.BaseAPI

interface AuthApi : BaseAPI{
    fun authLauncher(): AuthLauncher
}