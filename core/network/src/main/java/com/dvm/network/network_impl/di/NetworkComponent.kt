package com.dvm.network.network_impl.di

import com.dvm.network.network_api.di.NetworkApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [NetworkModule::class],
    dependencies = [NetworkDependencies::class]
)
internal interface NetworkComponent: NetworkApi {

    companion object {
        fun initAndGet(dependencies: NetworkDependencies): NetworkComponent =
            DaggerNetworkComponent.builder()
                .networkDependencies(dependencies)
                .build()
    }
}