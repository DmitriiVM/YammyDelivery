package com.dvm.auth.auth_impl.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dvm.auth.auth_impl.di.AuthComponentHolder
import com.dvm.ui.themes.YammyDeliveryTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import javax.inject.Inject

internal class RegisterFragment : Fragment() {

    @Inject
    lateinit var factory: RegisterViewModelFactory

    private val model: RegisterViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AuthComponentHolder.getComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            YammyDeliveryTheme(
                requireActivity().window
            ) {
                ProvideWindowInsets(consumeWindowInsets = false) {

                }
            }
        }
    }

}