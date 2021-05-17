package com.dvm.notifications

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ControlPoint
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dvm.appmenu_api.Drawer
import com.dvm.notifications.model.NotificationEvent
import com.dvm.notifications.model.NotificationState
import com.dvm.ui.components.AppBarIconMenu
import com.dvm.ui.components.TransparentAppBar
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import kotlinx.coroutines.launch

@Composable
internal fun Notifications(
    state: NotificationState,
    onEvent: (NotificationEvent) -> Unit
) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Drawer(drawerState = drawerState) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.statusBarsHeight())
            TransparentAppBar(
                title = { Text(stringResource(R.string.notification_appbar_title)) },
                navigationIcon = {
                    AppBarIconMenu {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                },
            )

            val lazyListState = rememberLazyListState()

            val lastItem = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            LaunchedEffect(lastItem) {
                lastItem?.let {
                    onEvent(NotificationEvent.VisibleItemChange(lastItem))
                }
            }


            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {

                items(state.notifications) { notification ->
                    Row(Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = notification.title)
                            Text(text = notification.text)
                        }
                        if (!notification.seen){
                            Icon(
                                imageVector = Icons.Default.ControlPoint,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}