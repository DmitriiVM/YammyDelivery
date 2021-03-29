package com.dvm.module_injector

interface ComponentHolder<C : BaseAPI, D : BaseDependencies> {

    fun init(dependencies: D)

    fun getApi(): C
}

interface BaseDependencies

interface BaseAPI