package com.dvm.appmenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun Drawer(
    navigator: Navigator,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    content: @Composable () -> Unit
) {
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
                    onClick = { navigator.navigateToMenuScreen() },
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
                    onClick = { navigator.navigateToMenuScreen() },
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
                    onClick = { navigator.navigateToMenuScreen() },
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
                    onClick = { navigator.navigateToMenuScreen() },
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
                    onClick = { navigator.navigateToMenuScreen() },
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
                    onClick = { navigator.navigateToMenuScreen() },
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
                    onClick = { navigator.navigateToMenuScreen() },
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
        IconButton(onClick = {  }) {
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

interface Navigator {
    fun navigateToDishScreen(dishId: String)
    fun navigateToMenuScreen()
}

