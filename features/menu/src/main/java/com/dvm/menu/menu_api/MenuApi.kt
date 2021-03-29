package com.dvm.menu.menu_api

import com.dvm.module_injector.BaseAPI

interface MenuApi: BaseAPI {
    fun menuLauncher(): MenuLauncher
}