package com.dvm.navigation.impl.di

import com.dvm.navigation.api.Navigator
import com.dvm.navigation.impl.DefaultNavigator
import org.koin.dsl.module

val navigatorModule = module {

    single<Navigator> {
        DefaultNavigator()
    }
}