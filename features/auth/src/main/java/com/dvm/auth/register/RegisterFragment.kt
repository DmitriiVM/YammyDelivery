package com.dvm.auth.register

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dvm.BaseFragment
import com.google.accompanist.insets.ProvideWindowInsets

internal class RegisterFragment : BaseFragment() {

    private val viewModel: RegisterViewModel by viewModels()

    @Composable
    override fun Content() {
        ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
            Registration(
                state = viewModel.state,
                onEvent = { viewModel.dispatch(it) }
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }
            }
        )
    }
}