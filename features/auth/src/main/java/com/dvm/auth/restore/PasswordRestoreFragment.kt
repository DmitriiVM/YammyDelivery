package com.dvm.auth.restore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dvm.ui.FragmentInsetsComposeView
import com.dvm.ui.themes.YammyDeliveryTheme
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

@AndroidEntryPoint
internal class PasswordRestoreFragment : Fragment() {

    private val model: PasswordRestoreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentInsetsComposeView (requireContext()).apply {
        setContent {
            YammyDeliveryTheme(
                requireActivity().window
            ) {
                ProvideWindowInsets(consumeWindowInsets = false) {
                    PasswordRestoration(
                        state = model.state,
                        onEvent = { model.dispatch(it) }
                    )
                }
            }
        }
    }
}