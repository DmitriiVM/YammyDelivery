package com.dvm.notifications_impl.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.navigation.api.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.notifications_api.domain.NotificationInteractor
import com.dvm.notifications_impl.presentation.model.NotificationEvent
import com.dvm.notifications_impl.presentation.model.NotificationState
import com.dvm.preferences.api.DatastoreRepository
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class NotificationViewModel(
    private val interactor: NotificationInteractor,
    private val navigator: Navigator,
    datastore: DatastoreRepository
) : ViewModel() {

    var state by mutableStateOf(NotificationState())
        private set

    init {
        datastore
            .authorized()
            .filter { !it }
            .onEach {
                navigator.goTo(Destination.Login(Destination.Notification))
            }
            .launchIn(viewModelScope)

        interactor
            .notifications()
            .onEach { notifications ->
                state = state.copy(notifications = notifications)
            }
            .launchIn(viewModelScope)
    }

    fun dispatch(event: NotificationEvent) {
        when (event) {
            is NotificationEvent.ChangeVisibleItem -> {
                viewModelScope.launch {
                    interactor.setSeen(
                        state.notifications,
                        event.lastItemPosition
                    )
                }
            }
            NotificationEvent.Back -> {
                navigator.back()
            }
        }
    }
}