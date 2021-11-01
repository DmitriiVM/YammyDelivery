package com.dvm.yammydelivery

import android.app.Application
import com.dvm.auth.authModule
import com.dvm.cart.cartModule
import com.dvm.database.impl.databaseModule
import com.dvm.dish.dishModule
import com.dvm.drawer.drawerModule
import com.dvm.menu.menuModule
import com.dvm.navigation.impl.navigatorModule
import com.dvm.network.impl.networkModule
import com.dvm.notifications.notificationModule
import com.dvm.order.orderModule
import com.dvm.preferences.impl.datastoreModule
import com.dvm.profile.profileModule
import com.dvm.splash.splashModule
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
                databaseModule,
                navigatorModule,
                updateModule,
                menuModule,
                authModule,
                cartModule,
                dishModule,
                drawerModule,
                notificationModule,
                orderModule,
                profileModule,
                splashModule
            )
        }
    }
}