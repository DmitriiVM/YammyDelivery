package com.dvm.order.orders

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import com.dvm.BaseFragment
import com.google.accompanist.insets.ProvideWindowInsets

internal class OrdersFragment : BaseFragment() {

    private val viewModel: OrdersViewModel by viewModels()

    @Composable
    override fun Content() {
        ProvideWindowInsets(consumeWindowInsets = false) {
            Ordering(
                state = viewModel.state,
                onEvent = { viewModel.dispatchEvent(it) }
            )
        }
    }
}