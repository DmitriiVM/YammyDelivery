package com.dvm.navigation.impl

import com.dvm.navigation.api.Navigator
import org.koin.dsl.module

val navigatorModule = module {

    single<Navigator> {
        DefaultNavigator()
    }
}