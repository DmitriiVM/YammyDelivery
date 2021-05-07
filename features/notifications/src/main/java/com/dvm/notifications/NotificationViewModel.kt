package com.dvm.notifications

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.db.api.NotificationRepository
import com.dvm.navigation.Navigator
import com.dvm.notifications.model.NotificationEvent
import com.dvm.notifications.model.NotificationState
import com.dvm.preferences.api.DatastoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val datastore: DatastoreRepository,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(NotificationState())
        private set

    private var previousLastItemPosition = 0

    init {
//        viewModelScope.launch {
//            if (!datastore.isAuthorized()){
//                navigator.goTo(Destination.Auth)
//            }
//        }

        notificationRepository
            .notifications()
            .onEach { notifications ->
                state = state.copy(notifications = notifications)
            }
            .launchIn(viewModelScope)
    }

    fun dispatch(event: NotificationEvent) {
        when (event) {
            NotificationEvent.BackClick -> {
                navigator.back()
            }
            is NotificationEvent.VisibleItemChange -> {
                viewModelScope.launch {
                    delay(1000)
                    notificationRepository.setSeen(
                        state.notifications
                            .subList(
                                fromIndex = previousLastItemPosition,
                                toIndex = event.lastItemPosition
                                    .coerceAtLeast(previousLastItemPosition)
                            )
                            .mapNotNull { it.id }
                    )
                    previousLastItemPosition = event.lastItemPosition
                }
            }
        }
    }
}