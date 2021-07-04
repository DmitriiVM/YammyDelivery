package com.dvm.notifications

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.db.api.NotificationRepository
import com.dvm.navigation.Navigator
import com.dvm.navigation.api.model.Destination
import com.dvm.notifications.model.NotificationEvent
import com.dvm.notifications.model.NotificationState
import com.dvm.preferences.api.DatastoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val navigator: Navigator,
    datastore: DatastoreRepository
) : ViewModel() {

    var state by mutableStateOf(NotificationState())
        private set

    private var previousLastItemPosition = 0

    init {
        datastore
            .authorized()
            .filter { !it }
            .onEach {
                navigator.goTo(Destination.Login(Destination.Notification))
            }
            .launchIn(viewModelScope)

        notificationRepository
            .notifications()
            .onEach { notifications ->
                state = state.copy(notifications = notifications)
            }
            .launchIn(viewModelScope)
    }

    fun dispatch(event: NotificationEvent) {
        when (event) {
            is NotificationEvent.VisibleItemChange -> {
                viewModelScope.launch {
                    delay(1000)
                    notificationRepository.setSeen(
                        state.notifications
                            .subList(
                                fromIndex = previousLastItemPosition,
                                toIndex = (event.lastItemPosition + 1)
                                    .coerceAtLeast(previousLastItemPosition)
                            )
                            .mapNotNull { it.id }
                    )
                    previousLastItemPosition = event.lastItemPosition
                }
            }
            NotificationEvent.BackClick -> {
                navigator.back()
            }
        }
    }
}