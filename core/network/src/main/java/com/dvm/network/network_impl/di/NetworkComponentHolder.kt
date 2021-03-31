package com.dvm.network.network_impl.di

import com.dvm.module_injector.ComponentHolder
import com.dvm.network.network_api.di.NetworkApi

object NetworkComponentHolder: ComponentHolder<NetworkApi, NetworkDependencies> {

    @Volatile
    private var networkComponent: NetworkComponent? = null

    override lateinit var dependencies: () -> NetworkDependencies

    override fun init() {
        if (networkComponent == null){
            synchronized(this){
                if (networkComponent == null){
                    networkComponent = NetworkComponent.initAndGet(dependencies())
                }
            }
        }
    }

    override fun getApi() : NetworkApi {
        checkNotNull(networkComponent) { "NetworkComponent is not initialized!" }
        return networkComponent!!
    }
}