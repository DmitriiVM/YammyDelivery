package com.dvm.dish.presentation

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.dvm.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import javax.inject.Inject

@AndroidEntryPoint
internal class DishFragment : BaseFragment() {

    @Inject
    lateinit var factory: DishViewModelAssistedFactory

    private val args: DishFragmentArgs by navArgs()

    private val viewModel: DishViewModel by viewModels {
        factory.create(args.dishId, this)
    }

    @Composable
    override fun Content() {
        ProvideWindowInsets(consumeWindowInsets = false) {
            Dish(
                state = viewModel.state,
                onEvent = { viewModel.dispatchEvent(it) }
            )
        }
    }
}