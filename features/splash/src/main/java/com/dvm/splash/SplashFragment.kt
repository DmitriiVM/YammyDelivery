package com.dvm.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dvm.ui.themes.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class SplashFragment : Fragment() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AppTheme(requireActivity().window) {
                Splash()
            }
        }
        viewModel // to initialize view model
    }
}