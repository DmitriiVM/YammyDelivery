package com.dvm.auth.restore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dvm.navigation.Navigator
import com.dvm.ui.themes.YammyDeliveryTheme
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import javax.inject.Inject

@AndroidEntryPoint
internal class PasswordRestoreFragment : Fragment() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var factory: PasswordRestoreViewModelFactory

    private val model: PasswordRestoreViewModel by viewModels {
        factory
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
                    PasswordRestoration(
                        state = model.state,
                        navigator = navigator,
                        onEvent = { model.dispatch(it) }
                    )
                }
            }
        }
    }
}