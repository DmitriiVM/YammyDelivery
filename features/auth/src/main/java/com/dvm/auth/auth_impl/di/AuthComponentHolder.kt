package com.dvm.auth.auth_impl.di

import com.dvm.auth.auth_api.AuthApi
import com.dvm.module_injector.ComponentHolder

object AuthComponentHolder: ComponentHolder<AuthApi, AuthDependencies> {

    private var authComponent: AuthComponent? = null

    override lateinit var dependencies: () -> AuthDependencies

    override fun init() {
        if (authComponent == null){
            synchronized(this){
                if (authComponent == null){
                    authComponent = AuthComponent.initAndGet(dependencies())
                }
            }
        }
    }

    override fun getApi(): AuthApi = getComponent()

    override fun destroy() {
        authComponent = null
    }

    internal fun getComponent(): AuthComponent {
        checkNotNull(authComponent){ "AuthComponent is not initialized!"}
        return authComponent!!
    }
}