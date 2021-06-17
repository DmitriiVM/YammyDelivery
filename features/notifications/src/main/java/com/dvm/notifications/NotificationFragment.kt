package com.dvm.notifications

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import com.dvm.BaseFragment
import dev.chrisbanes.accompanist.insets.ExperimentalAnimatedInsets
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

internal class NotificationFragment : BaseFragment() {

    private val viewModel: NotificationViewModel by viewModels()

    @OptIn(ExperimentalAnimatedInsets::class)
    @Composable
    override fun Content() {
        ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
            Notifications(
                state = viewModel.state,
                onEvent = { viewModel.dispatch(it) }
            )
        }
    }


}