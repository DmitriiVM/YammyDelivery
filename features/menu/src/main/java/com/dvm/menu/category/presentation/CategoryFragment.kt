package com.dvm.menu.category.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.dvm.navigation.Navigator
import com.dvm.ui.themes.YammyDeliveryTheme
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import javax.inject.Inject

@AndroidEntryPoint
internal class CategoryFragment : Fragment() {

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var factory: CategoryViewModelAssistedFactory

    private val args: CategoryFragmentArgs by navArgs()

    private val viewModel: CategoryViewModel by viewModels {
        factory.create(args.id)
    }

    @ExperimentalFoundationApi
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
                    Category(
                        state = viewModel.state,
                        navigator = navigator,
                        onEvent = { viewModel.dispatch(it) }
                    )
                }
            }
        }
    }
}