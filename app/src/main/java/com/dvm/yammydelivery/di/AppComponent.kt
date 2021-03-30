package com.dvm.yammydelivery.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}