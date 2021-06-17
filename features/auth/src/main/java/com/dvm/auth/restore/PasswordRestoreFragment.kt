package com.dvm.auth.restore

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import com.dvm.BaseFragment
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

internal class PasswordRestoreFragment : BaseFragment() {

    private val viewModel: PasswordRestoreViewModel by viewModels()

    @Composable
    override fun Content() {
        ProvideWindowInsets(consumeWindowInsets = false) {
            PasswordRestoration(
                state = viewModel.state,
                onEvent = { viewModel.dispatch(it) }
            )
        }
    }
}