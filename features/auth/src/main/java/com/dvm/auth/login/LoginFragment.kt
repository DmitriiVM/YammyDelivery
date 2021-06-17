package com.dvm.auth.login

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import com.dvm.BaseFragment
import dev.chrisbanes.accompanist.insets.ExperimentalAnimatedInsets
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

internal class LoginFragment : BaseFragment() {

    private val viewModel: LoginViewModel by viewModels()

    @OptIn(ExperimentalAnimatedInsets::class)
    @Composable
    override fun Content() {
        ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
            Login(
                state = viewModel.state,
                onEvent = { viewModel.dispatch(it) }
            )
        }
    }
}