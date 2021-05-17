package com.dvm.dish.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.dvm.dish.dish_impl.Dish
import com.dvm.ui.themes.YammyDeliveryTheme
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import javax.inject.Inject

@AndroidEntryPoint
internal class DishFragment : Fragment() {

    @Inject
    lateinit var factory: DishViewModelAssistedFactory

    private val args: DishFragmentArgs by navArgs()

    private val viewModel: DishViewModel by viewModels {
        factory.create(args.dishId, this)
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
                    Dish(
                        state = viewModel.state,
                        onEvent = { viewModel.dispatchEvent(it) }
                    )
                }
            }
        }
    }
}