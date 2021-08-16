package com.dvm.notifications

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import com.dvm.BaseFragment
import com.google.accompanist.insets.ProvideWindowInsets

internal class NotificationFragment : BaseFragment() {

    private val viewModel: NotificationViewModel by viewModels()

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