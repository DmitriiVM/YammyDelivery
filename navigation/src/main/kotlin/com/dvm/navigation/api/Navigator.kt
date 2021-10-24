package com.dvm.navigation.api

import com.dvm.navigation.api.model.Destination
import kotlinx.coroutines.flow.SharedFlow

interface Navigator {

    val destination: SharedFlow<Destination>
    val currentDestination: Destination?

    fun back()
    fun goTo(destination: Destination)
}