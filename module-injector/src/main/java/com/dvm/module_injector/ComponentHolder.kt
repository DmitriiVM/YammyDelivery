package com.dvm.module_injector

interface ComponentHolder<C : BaseAPI, D : BaseDependencies> {

    val dependencies: () -> BaseDependencies

    fun init()

    fun getApi(): C
}

interface BaseDependencies

interface BaseAPI