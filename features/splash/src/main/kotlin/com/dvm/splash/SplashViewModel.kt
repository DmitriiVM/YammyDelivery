package com.dvm.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.preferences.api.DatastoreRepository
import com.dvm.updateservice.api.UpdateService
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

internal class SplashViewModel(
    private val updateService: UpdateService,
    private val datastore: DatastoreRepository,
    private val navigator: Navigator,
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updateDuration = measureTimeMillis {
                    withTimeout(TIMEOUT) {
                        updateService.updateAll()
                    }
                    datastore.setUpdateError(false)
                }
                if (updateDuration < MIN_SPLASH_DURATION) {
                    delay(MIN_SPLASH_DURATION - updateDuration)
                }
            } catch (exception: CancellationException) {
                throw CancellationException()
            } catch (exception: Exception) {
                datastore.setUpdateError(true)
            } finally {
                navigator.goTo(Destination.Main)
            }
        }
    }

    companion object {
        private const val TIMEOUT = 15000L
        private const val MIN_SPLASH_DURATION = 3000L
    }
}