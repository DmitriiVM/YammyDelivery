package com.dvm.appmenu

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dvm.appmenu.model.AppMenuEvent
import com.dvm.ui.components.Alert
import com.dvm.ui.components.AlertButton
import com.dvm.ui.themes.DecorColors
import com.dvm.utils.BackPressHandler
import com.dvm.utils.DrawerItem
import com.google.accompanist.insets.navigationBarsPadding
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
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
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.init(context.applicationContext)
    }

    LaunchedEffect(drawerState.isOpen) {
        if (drawerState.isOpen) {
            keyboardController?.hide()
        }
    }

    if (drawerState.isOpen) {
        BackPressHandler {
            scope.launch {
                drawerState.close()
            }
        }
    }

    ModalDrawer(
        drawerState = drawerState,
        content = {
            val configuration = LocalConfiguration.current

            val modifier = when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    Modifier.navigationBarsPadding()
                }
                else -> Modifier
            }
            Surface(modifier.fillMaxSize()) {
                content()
            }
        },
        drawerContent = {

            Box(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                Circles()

                Column {

                    DrawerHeader(
                        name = state.name,
                        email = state.email,
                        onProfileClick = {
                            viewModel.onEvent(
                                AppMenuEvent.ItemClick(DrawerItem.PROFILE)
                            )
                        },
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

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp, horizontal = 8.dp)
                            .height(3.dp)
                            .background(
                                color = DecorColors.DARK_BLUE.color.copy(alpha = 0.6f),
                                shape = RoundedCornerShape(1.dp)
                            )
                    )

                    DrawerItem(
                        painter = painterResource(R.drawable.icon_home),
                        text = stringResource(R.string.drawer_item_main),
                        selected = selected == DrawerItem.MAIN,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                viewModel.onEvent(AppMenuEvent.ItemClick(DrawerItem.MAIN))
                            }
                        }
                    )
                    DrawerItem(
                        painter = painterResource(R.drawable.icon_menu),
                        text = stringResource(R.string.drawer_item_menu),
                        selected = selected == DrawerItem.MENU,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                viewModel.onEvent(AppMenuEvent.ItemClick(DrawerItem.MENU))
                            }
                        }
                    )
                    DrawerItem(
                        painter = painterResource(R.drawable.icon_favorite),
                        text = stringResource(R.string.drawer_item_favorite),
                        selected = selected == DrawerItem.FAVORITE,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                viewModel.onEvent(AppMenuEvent.ItemClick(DrawerItem.FAVORITE))
                            }
                        }
                    )
                    DrawerItem(
                        painter = painterResource(R.drawable.icon_cart),
                        text = stringResource(R.string.drawer_item_cart),
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
                        painter = painterResource(R.drawable.icon_profile),
                        text = stringResource(R.string.drawer_item_profile),
                        selected = selected == DrawerItem.PROFILE,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                viewModel.onEvent(AppMenuEvent.ItemClick(DrawerItem.PROFILE))
                            }
                        }
                    )
                    DrawerItem(
                        painter = painterResource(R.drawable.icon_order),
                        text = stringResource(R.string.drawer_item_orders),
                        selected = selected == DrawerItem.ORDERS,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                viewModel.onEvent(AppMenuEvent.ItemClick(DrawerItem.ORDERS))
                            }
                        }
                    )
                    DrawerItem(
                        painter = painterResource(R.drawable.icon_notification),
                        text = stringResource(R.string.drawer_item_notifications),
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
        }
    )

    if (!state.alertMessage.isNullOrEmpty()) {
        val onDismiss = { viewModel.onEvent(AppMenuEvent.DismissAlert) }
        Alert(
            message = state.alertMessage,
            onDismiss = onDismiss,
            buttons = {
                AlertButton(
                    text = { Text(stringResource(R.string.common_no)) },
                    onClick = onDismiss
                )
                AlertButton(
                    text = { Text(stringResource(R.string.common_yes)) },
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

    val colors = remember {
        DecorColors.values()
            .toList()
            .filterNot { it == DecorColors.YELLOW }
            .shuffled()
            .map { it.color.copy(alpha = 0.7f) }
            .take(3)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.radialGradient(
                    colors = colors,
                    center = Offset(0f, -50f),
                    radius = 1000f
                )
            )
            .padding(top = 100.dp)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
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
            val iconId = if (email.isNotEmpty()) {
                R.drawable.icon_logout
            } else {
                R.drawable.icon_login
            }
            Icon(
                painter = painterResource(iconId),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun DrawerItem(
    painter: Painter,
    text: String,
    count: Int = 0,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 8.dp, vertical = 2.dp)
            .clickable { onClick() }
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .wrapContentHeight(),
            tint = if (selected) {
                DecorColors.DARK_BLUE.color
            } else {
                MaterialTheme.colors.onSurface
            }
        )

        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .weight(1f)
                .padding(start = 24.dp)
                .wrapContentHeight(),
            color = if (selected) {
                DecorColors.DARK_BLUE.color
            } else {
                MaterialTheme.colors.onSurface
            }
        )

        if (count > 0) {
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.wrapContentHeight(),
                color = DecorColors.BLUE.color
            )
        }
    }

    if (selected) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(2.dp)
                .background(
                    color = DecorColors.DARK_BLUE.color.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(1.dp)
                )
        )
    }
}

@Composable
private fun Circles() {
    Canvas(
        modifier = Modifier
            .padding(top = 250.dp)
            .fillMaxSize()
    ) {

        val color = DecorColors.DARK_BLUE.color.copy(alpha = 0.05f)
        val style = Stroke(width = 30f)

        drawArc(
            color = color,
            startAngle = 90f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(size.width - 200f, 0f),
            size = Size(600f, 600f),
            style = style
        )
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(-1600f, 500f),
            size = Size(2000f, 2000f),
            style = style
        )
        drawArc(
            color = color,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(200f, 1300f),
            size = Size(800f, 800f),
            style = style
        )
        drawCircle(
            color = color,
            center = Offset(700f, 900f),
            radius = 70f,
            style = style
        )
    }
}