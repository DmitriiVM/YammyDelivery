package com.dvm.appmenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dvm.navigation.Navigator
import com.dvm.navigation.api.model.Destination
import kotlinx.coroutines.launch

@Composable
fun Drawer(
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    navigator: Navigator,
    content: @Composable() () -> Unit
) {

//    val viewModel: AppMenuViewModel = viewModel()

    val scope = rememberCoroutineScope()

    ModalDrawer(
        drawerState = drawerState,
        content = content,
//        drawerBackgroundColor = temp.copy(alpha = 0.9f),
        drawerContent = {
            Column {
                DrawerHeader()
                val modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp)


                DrawerItem(
                    text = "Главная",
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navigator.goTo(Destination.Main)
                        }
//                        viewModel.onEvent(DrawerItem.MAIN)
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
                            navigator.goTo(Destination.Menu)
                        }
//                        viewModel.onEvent(DrawerItem.MENU)
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
                            navigator.goTo(Destination.Favorite)
                        }
//                        viewModel.onEvent(DrawerItem.FAVORITE)
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
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navigator.goTo(Destination.Cart)
                        }
//                        viewModel.onEvent(DrawerItem.CART)
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
                            navigator.goTo(Destination.Profile)
                        }
//                        viewModel.onEvent(DrawerItem.PROFILE)
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
                            navigator.goTo(Destination.Orders)
                        }
//                        viewModel.onEvent(DrawerItem.ORDERS)
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
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navigator.goTo(Destination.Notification)
                        }
//                        viewModel.onEvent(DrawerItem.NOTIFICATION)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_notification),
                            contentDescription = null,
                            modifier = modifier
                        )
                    }
                )
                DrawerItem(
                    text = "SIGN_IN",
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navigator.goTo(Destination.Login)
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
}

@Composable
fun DrawerHeader() {
    Spacer(modifier = Modifier.height(100.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.padding(10.dp)) {
            Text(text = "Иван Иванов")
            Text(text = "mail@mail.ru")
        }
        IconButton(onClick = { }) {
            Icon(
                painter = painterResource(id = R.drawable.icon_logout),
                contentDescription = null,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
    Divider()
}

@Composable
private fun DrawerItem(
    text: String,
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
    }
}

enum class DrawerItem {
    MAIN,
    MENU,
    FAVORITE,
    CART,
    PROFILE,
    ORDERS,
    NOTIFICATION,
    SIGN_IN,
    SIGN_OUT
}

