package com.dvm.updateservice.impl

import com.dvm.updateservice.api.UpdateService
import org.koin.dsl.module

val updateModule = module {

    factory<UpdateService> {
        DefaultUpdateService(
            categoryInteractor = get(),
            dishInteractor = get(),
            profileInteractor = get(),
            orderInteractor = get(),
            favoriteInteractor = get(),
            datastore = get()
        )
    }
}