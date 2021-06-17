package com.dvm.menu.main

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import com.dvm.BaseFragment
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

internal class MainFragment : BaseFragment() {

    private val viewModel: MainViewModel by viewModels()

    @Composable
    override fun Content() {
        ProvideWindowInsets(consumeWindowInsets = false) {
            Main(
                state = viewModel.state,
                onEvent = { viewModel.dispatch(it) }
            )
        }
    }
}