package com.dvm.navigation.impl

import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import kotlinx.coroutines.flow.MutableSharedFlow

internal class DefaultNavigator : Navigator {

    override val destination: MutableSharedFlow<Destination> = MutableSharedFlow(1)
    override val currentDestination: Destination?
        get() = destination.replayCache.firstOrNull()

    override fun back() {
        destination.tryEmit(Destination.Back)
    }

    override fun goTo(destination: Destination) {
        this.destination.tryEmit(destination)
    }
}