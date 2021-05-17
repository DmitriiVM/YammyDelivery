package com.dvm.appmenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dvm.appmenu.model.AppMenuEvent
import com.dvm.ui.components.Alert
import com.dvm.ui.components.AlertButton
import kotlinx.coroutines.launch

@Composable
fun AppDrawer(
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    content: @Composable () -> Unit
) {

    val viewModel: AppMenuViewModel = viewModel()
    val state = viewModel.state

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.init(context.applicationContext)
    }

    val scope = rememberCoroutineScope()

    ModalDrawer(
        drawerState = drawerState,
        content = content,
//        drawerBackgroundColor = temp.copy(alpha = 0.9f),
        drawerContent = {
            Column {

                DrawerHeader(
                    name = state.name,
                    email = state.email,
                    onProfileClick = { viewModel.onEvent(AppMenuEvent.ItemClick(DrawerItem.PROFILE)) },
                    onAuthButtonClick = {
                        if (state.email.isEmpty()) {
                            scope.launch {
                                drawerState.close()
                                viewModel.onEvent(AppMenuEvent.AuthClick)
                            }
                        } else {
                            viewModel.onEvent(AppMenuEvent.AuthClick)
                        }
                    }
                )
                val modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp)

                DrawerItem(
                    text = "Главная",
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            viewModel.onEvent(AppMenuEvent.ItemClick(DrawerItem.MAIN))
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_home),
                            contentDescription = null,
                            modifier = modifier
                        )
                    }
                )
                DrawerItem(
                    text = "Меню",
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            viewModel.onEvent(AppMenuEvent.ItemClick(DrawerItem.MENU))
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_menu),
                            contentDescription = null,
                            modifier = modifier
                        )
                    }
                )
                DrawerItem(
                    text = "Избранное",
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            viewModel.onEvent(AppMenuEvent.ItemClick(DrawerItem.FAVORITE))
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_favorite),
                            contentDescription = null,
                            modifier = modifier
                        )
                    }
                )
                DrawerItem(
                    text = "Корзина",
                    count = state.cartQuantity,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            viewModel.onEvent(AppMenuEvent.ItemClick(DrawerItem.CART))
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_cart),
                            contentDescription = null,
                            modifier = modifier
                        )
                    }
                )
                DrawerItem(
                    text = "Профиль",
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            viewModel.onEvent(AppMenuEvent.ItemClick(DrawerItem.PROFILE))
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_profile),
                            contentDescription = null,
                            modifier = modifier
                        )
                    }
                )
                DrawerItem(
                    text = "Заказы",
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            viewModel.onEvent(AppMenuEvent.ItemClick(DrawerItem.ORDERS))
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_order),
                            contentDescription = null,
                            modifier = modifier
                        )
                    }
                )
                DrawerItem(
                    text = "Уведомления",
                    count = state.newNotificationCount,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            viewModel.onEvent(AppMenuEvent.ItemClick(DrawerItem.NOTIFICATION))
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_notification),
                            contentDescription = null,
                            modifier = modifier
                        )
                    }
                )
            }
        }
    )

//    state.alertMessage?.let {
//        Alert(
//            message = state.alertMessage,
//            onDismiss = { onEvent(MainEvent.DismissAlert) }
//        ) {
//            AlertButton(onClick = { onEvent(MainEvent.DismissAlert) })
//        }
//    }

    if (!state.alertMessage.isNullOrEmpty()) {
        val onDismiss = { viewModel.onEvent(AppMenuEvent.DismissAlert) }
        Alert(
            message = state.alertMessage,
            onDismiss = onDismiss,
            buttons = {
                AlertButton(
                    text = { Text("Нет") },
                    onClick = onDismiss
                )
                AlertButton(
                    text = { Text("Да") },
                    onClick = { viewModel.onEvent(AppMenuEvent.LogoutClick) }
                )
            }
        )
    }
}

@Composable
fun DrawerHeader(
    name: String,
    email: String,
    onProfileClick: () -> Unit,
    onAuthButtonClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(100.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            Modifier
                .padding(10.dp)
                .clickable { onProfileClick() }) {
            Text(text = name)
            Text(text = email)
        }
        IconButton(
            onClick = { onAuthButtonClick() }
        ) {
            if (email.isNotEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_logout),
                    contentDescription = null,
                    modifier = Modifier.padding(10.dp)
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.icon_login),
                    contentDescription = null,
                    modifier = Modifier.padding(10.dp)
                )
            }

        }
    }
    Divider()
}

@Composable
private fun DrawerItem(
    text: String,
    count: Int = 0,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Text(
            text = text,
            modifier = Modifier
                .padding(start = 10.dp)
                .clickable { onClick() }
        )
        if (count > 0) {
            Text(text = "        $count")
        }
    }
}

enum class DrawerItem {
    MAIN,
    MENU,
    FAVORITE,
    CART,
    PROFILE,
    ORDERS,
    NOTIFICATION
}

