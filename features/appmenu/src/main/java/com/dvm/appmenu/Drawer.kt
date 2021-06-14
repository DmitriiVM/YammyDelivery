package com.dvm.appmenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dvm.appmenu.model.AppMenuEvent
import com.dvm.ui.components.Alert
import com.dvm.ui.components.AlertButton
import com.dvm.ui.components.horizontalGradient
import com.dvm.ui.themes.DecorColors
import com.dvm.utils.BackPressHandler
import com.dvm.utils.DrawerItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun AppDrawer(
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    selected: DrawerItem = DrawerItem.MAIN,
    content: @Composable () -> Unit
) {

    val viewModel: AppMenuViewModel = viewModel()
    val state = viewModel.state

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        viewModel.init(context.applicationContext)
    }
    LaunchedEffect(drawerState){
        if (drawerState.isOpen){
            keyboardController?.hide()
        }
    }

    val scope = rememberCoroutineScope()

    if (drawerState.isOpen) {
        BackPressHandler {
            scope.launch {
                drawerState.close()
            }
        }
    }

    Surface {
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
                    Spacer(modifier = Modifier.height(18.dp))


                    DrawerItem(
                        painter = painterResource(id = R.drawable.icon_home),
                        text = "Главная",
                        selected = selected == DrawerItem.MAIN,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                viewModel.onEvent(AppMenuEvent.ItemClick(DrawerItem.MAIN))
                            }
                        }
                    )
                    DrawerItem(
                        painter = painterResource(id = R.drawable.icon_menu),
                        text = "Меню",
                        selected = selected == DrawerItem.MENU,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                viewModel.onEvent(AppMenuEvent.ItemClick(DrawerItem.MENU))
                            }
                        }
                    )
                    DrawerItem(
                        painter = painterResource(id = R.drawable.icon_favorite),
                        text = "Избранное",
                        selected = selected == DrawerItem.FAVORITE,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                viewModel.onEvent(AppMenuEvent.ItemClick(DrawerItem.FAVORITE))
                            }
                        }
                    )
                    DrawerItem(
                        painter = painterResource(id = R.drawable.icon_cart),
                        text = "Корзина",
                        count = state.cartQuantity,
                        selected = selected == DrawerItem.CART,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                viewModel.onEvent(AppMenuEvent.ItemClick(DrawerItem.CART))
                            }
                        }
                    )
                    DrawerItem(
                        painter = painterResource(id = R.drawable.icon_profile),
                        text = "Профиль",
                        selected = selected == DrawerItem.PROFILE,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                viewModel.onEvent(AppMenuEvent.ItemClick(DrawerItem.PROFILE))
                            }
                        }
                    )
                    DrawerItem(
                        painter = painterResource(id = R.drawable.icon_order),
                        text = "Заказы",
                        selected = selected == DrawerItem.ORDERS,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                viewModel.onEvent(AppMenuEvent.ItemClick(DrawerItem.ORDERS))
                            }
                        }
                    )
                    DrawerItem(
                        painter = painterResource(id = R.drawable.icon_notification),
                        text = "Уведомления",
                        count = state.newNotificationCount,
                        selected = selected == DrawerItem.NOTIFICATION,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                viewModel.onEvent(AppMenuEvent.ItemClick(DrawerItem.NOTIFICATION))
                            }
                        }
                    )
                }
            }
        )

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
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            Modifier.clickable { onProfileClick() }) {
            Text(
                text = name,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Text(
                text = email,
                style = MaterialTheme.typography.body2
            )
        }
        IconButton(
            onClick = { onAuthButtonClick() }
        ) {
            if (email.isNotEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_logout),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.icon_login),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
    Divider()
}

@Composable
private fun DrawerItem(
    painter: Painter,
    text: String,
    count: Int = 0,
    selected: Boolean,
    onClick: () -> Unit
) {
    val modifier = if (selected) {
        Modifier
            .horizontalGradient(
                DecorColors.BLUE.color.copy(alpha = 0.4f),
                DecorColors.GREEN.color.copy(alpha = 0.2f)
            )
    } else {
        Modifier
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 8.dp, vertical = 2.dp)
            .clickable { onClick() }
            .then(modifier)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .wrapContentHeight()
        )
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .weight(1f)
                .padding(start = 24.dp)
                .wrapContentHeight()

        )
        if (count > 0) {
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .wrapContentHeight()
            )
        }
    }
}