package com.dvm.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dvm.ui.FragmentInsetsComposeView
import com.dvm.ui.YammyDeliveryScreen
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.ExperimentalAnimatedInsets
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

@AndroidEntryPoint
internal class NotificationFragment: Fragment() {

    private val viewModel: NotificationViewModel by viewModels()

    @OptIn(ExperimentalAnimatedInsets::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentInsetsComposeView (requireContext()).apply {
        setContent {
            YammyDeliveryScreen(requireActivity()) {
                ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                    Notifications(
                        state = viewModel.state,
                        onEvent = { viewModel.dispatch(it) }
                    )
                }
            }
        }
    }
}