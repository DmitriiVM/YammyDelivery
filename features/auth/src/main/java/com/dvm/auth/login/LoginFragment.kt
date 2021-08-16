package com.dvm.auth.login

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import com.dvm.BaseFragment
import com.google.accompanist.insets.ProvideWindowInsets

internal class LoginFragment : BaseFragment() {

    private val viewModel: LoginViewModel by viewModels()

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