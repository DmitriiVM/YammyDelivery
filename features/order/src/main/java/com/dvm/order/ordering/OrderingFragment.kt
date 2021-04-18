package com.dvm.order.ordering

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dvm.navigation.Navigator
import com.dvm.ui.themes.YammyDeliveryTheme
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.ExperimentalAnimatedInsets
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import javax.inject.Inject

@AndroidEntryPoint
internal class OrderingFragment: Fragment() {

    @Inject
    lateinit var navigator: Navigator

    private val viewModel: OrderingViewModel by viewModels()

    @OptIn(ExperimentalAnimatedInsets::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            YammyDeliveryTheme(
                requireActivity().window
            ) {
                ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                    viewModel.state.let { state ->
                        Ordering(
                            state = state,
                            navigator = navigator,
                            onEvent = { viewModel.dispatchEvent(it) }
                        )
                    }
                }
            }
        }
    }
}