package com.dvm.notifications

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dvm.appmenu_api.Drawer
import com.dvm.notifications.model.NotificationEvent
import com.dvm.notifications.model.NotificationState
import com.dvm.ui.components.AppBarIconMenu
import com.dvm.ui.components.DefaultAppBar
import com.dvm.utils.DrawerItem
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeApi::class)
@Composable
internal fun Notifications(
    state: NotificationState,
    onEvent: (NotificationEvent) -> Unit
) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Drawer(
        drawerState = drawerState,
        selected = DrawerItem.NOTIFICATION
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.statusBarsHeight())
            DefaultAppBar(
                title = { Text(stringResource(R.string.notification_appbar_title)) },
                navigationIcon = {
                    AppBarIconMenu {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                }
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
                    .padding(start = 25.dp, top = 20.dp)
                    .navigationBarsPadding()
            ) {
                items(state.notifications) { notification ->
                    NotificationItem(
                        title = notification.title,
                        text = notification.text,
                        seen = notification.seen
                    )
                }
            }
        }
    }
}

@Composable
private fun NotificationItem(
    title: String,
    text: String,
    seen: Boolean
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
                .wrapContentHeight()
        ) {
            Text(
                text = title,
                color = if (!seen) {
                    MaterialTheme.colors.primary
                } else {
                    MaterialTheme.colors.onSurface
                },
                modifier = Modifier.weight(1f)
            )
            if (!seen) {
                Text(
                    text = "⬤",
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(horizontal = 15.dp)
                )
            }
        }
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
            Text(
                text = text,
                modifier = Modifier.padding(end = 50.dp, bottom = 10.dp)
            )
        }
        Divider()
    }
}