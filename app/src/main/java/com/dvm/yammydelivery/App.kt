package com.dvm.yammydelivery

import android.app.Application
import com.dvm.appmenu.drawerModule
import com.dvm.auth.authModule
import com.dvm.cart.cartModule
import com.dvm.database.impl.di.databaseModule
import com.dvm.dish.dishModule
import com.dvm.menu.menuModule
import com.dvm.navigation.impl.di.navigatorModule
import com.dvm.network.impl.di.networkModule
import com.dvm.notifications.notificationModule
import com.dvm.order.orderModule
import com.dvm.preferences.impl.di.datastoreModule
import com.dvm.profile.profileModule
import com.dvm.splash.splashModule
import com.dvm.updateservice.impl.di.updateModule
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