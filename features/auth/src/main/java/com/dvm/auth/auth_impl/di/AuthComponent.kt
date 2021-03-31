package com.dvm.auth.auth_impl.di

import com.dvm.auth.auth_api.AuthApi
import com.dvm.auth.auth_impl.login.LoginFragment
import com.dvm.auth.auth_impl.register.RegisterFragment
import com.dvm.auth.auth_impl.restore.PasswordRestoreFragment
import com.dvm.utils.di.FeatureScope
import dagger.Component

@FeatureScope
@Component(
    modules = [AuthModule::class],
    dependencies = [AuthDependencies::class]
)
internal interface AuthComponent: AuthApi {

    fun inject(authFragment: LoginFragment)
    fun inject(registerFragment: RegisterFragment)
    fun inject(restoreFragment: PasswordRestoreFragment)

    companion object {
        fun initAndGet(authDependencies: AuthDependencies): AuthComponent {
            return DaggerAuthComponent.builder()
                .authDependencies(authDependencies)
                .build()
        }
    }
}