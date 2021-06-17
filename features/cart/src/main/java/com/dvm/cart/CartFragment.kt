package com.dvm.cart

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import com.dvm.BaseFragment
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

internal class CartFragment : BaseFragment() {

    private val viewModel: CartViewModel by viewModels()

    @Composable
    override fun Content() {
        ProvideWindowInsets(consumeWindowInsets = false) {
            Cart(
                state = viewModel.state,
                onEvent = { viewModel.dispatchEvent(it) }
            )
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }
}