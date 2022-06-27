package com.dvm.yammydelivery

import android.app.Application
import com.dvm.auth_impl.di.authModule
import com.dvm.cart_impl.di.cartModule
import com.dvm.drawer_impl.di.drawerModule
import com.dvm.menu_impl.di.menuModule
import com.dvm.navigation.impl.navigatorModule
import com.dvm.network.networkModule
import com.dvm.notifications_impl.di.notificationModule
import com.dvm.order_impl.di.ordersModule
import com.dvm.preferences.impl.datastoreModule
import com.dvm.profile_impl.di.profileModuleTmp
import com.dvm.splash_impl.splashModule
import com.dvm.updateservice.impl.updateModule
import com.dvm.yammydelivery.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

internal class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                datastoreModule,
                networkModule,
                navigatorModule,
                updateModule,
                menuModule,
                authModule,
                cartModule,
                drawerModule,
                notificationModule,
                ordersModule,
                profileModuleTmp,
                splashModule
            )
        }
    }
}