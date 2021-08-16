package com.dvm.order.order

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.dvm.BaseFragment
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class OrderFragment : BaseFragment() {

    @Inject
    lateinit var factory: OrderViewModelAssistedFactory

    private val args: OrderFragmentArgs by navArgs()

    private val viewModel: OrderViewModel by viewModels {
        factory.create(args.orderId, this)
    }

    @Composable
    override fun Content() {
        ProvideWindowInsets(consumeWindowInsets = false) {
            Order(
                state = viewModel.state,
                onEvent = { viewModel.dispatchEvent(it) }
            )
        }
    }
}