package com.dvm.order_impl.presentation.orders

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.DrawerValue
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dvm.drawer_api.DrawerLauncher
import com.dvm.order_api.domain.model.Order
import com.dvm.order_impl.presentation.orders.model.OrderStatus
import com.dvm.order_impl.presentation.orders.model.OrdersEvent
import com.dvm.order_impl.presentation.orders.model.OrdersState
import com.dvm.ui.components.AppBarIconMenu
import com.dvm.ui.components.DefaultAppBar
import com.dvm.ui.components.EmptyPlaceholder
import com.dvm.ui.components.LoadingScrim
import com.dvm.ui.components.verticalGradient
import com.dvm.ui.themes.DecorColors
import com.dvm.utils.DrawerItem
import com.dvm.utils.extensions.format
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.getStateViewModel
import com.dvm.ui.R as CoreR

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun OrdersScreen(
    viewModel: OrdersViewModel = getStateViewModel(),
    drawerLauncher: DrawerLauncher = get()
) {
    val state: OrdersState = viewModel.state

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    drawerLauncher.Drawer(
        drawerState = drawerState,
        selected = DrawerItem.ORDERS
    ) {

        val color by rememberSaveable {
            mutableStateOf(DecorColors.values().random())
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalGradient(color.color.copy(alpha = 0.15f))
        ) {
            Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            DefaultAppBar(
                title = { Text(stringResource(CoreR.string.orders_appbar_title)) },
                navigationIcon = {
                    AppBarIconMenu {
                        scope.launch { drawerState.open() }
                    }
                },
            )

            val pagerState = rememberPagerState()

            TabRow(
                selectedTabIndex = state.status.ordinal,
                backgroundColor = MaterialTheme.colors.surface,
                contentColor = contentColorFor(MaterialTheme.colors.surface),
                indicator = { tabPositions ->
                    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.primary) {
                        TabRowDefaults.Indicator(
                            Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                        )
                    }

                }
            ) {
                Tab(
                    text = { Text(stringResource(CoreR.string.orders_tab_actual)) },
                    selected = pagerState.currentPage == 0,
                    onClick = {
                        scope.launch {
                            viewModel.dispatch(OrdersEvent.StatusSelect(OrderStatus.ACTUAL))
                            pagerState.animateScrollToPage(0)
                        }
                    },
                )
                Tab(
                    text = { Text(stringResource(CoreR.string.orders_tab_completed)) },
                    selected = pagerState.currentPage == 1,
                    onClick = {
                        scope.launch {
                            viewModel.dispatch(OrdersEvent.StatusSelect(OrderStatus.COMPLETED))
                            pagerState.animateScrollToPage(1)
                        }
                    },
                )
            }
            HorizontalPager(
                count = 2,
                state = pagerState
            ) {
                if (state.empty) {
                    EmptyPlaceholder(
                        resId = com.dvm.ui.R.raw.empty_image,
                        text = stringResource(CoreR.string.orders_empty_placeholder)
                    )
                } else {
                    LazyColumn(
                        Modifier
                            .fillMaxSize()
                            .padding(start = 30.dp)
                            .navigationBarsPadding()
                    ) {
                        items(state.orders) { order ->
                            OrderItem(
                                order = order,
                                onOrderClick = { viewModel.dispatch(OrdersEvent.Order(order.id)) }
                            )
                        }
                    }
                }
            }
        }
    }

    if (state.progress) {
        LoadingScrim()
    }
}

@Composable
private fun OrderItem(
    order: Order,
    onOrderClick: () -> Unit
) {
    Column(
        Modifier.clickable(onClick = onOrderClick)
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
            Text(
                text = order.createdAt.format(),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 15.dp, bottom = 8.dp)
            )
        }
        Row {
            Text(
                text = stringResource(
                    CoreR.string.orders_order_number,
                    order.createdAt.time.toString().take(5)
                ),
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colors.primary
            )
            Text(
                text = stringResource(
                    CoreR.string.dish_item_price,
                    order.total
                ),
                modifier = Modifier.padding(end = 8.dp),
                color = MaterialTheme.colors.primary
            )
        }
        Row {
            Text(
                text = order.address,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = order.status,
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
            )
        }
        Divider()
    }
}