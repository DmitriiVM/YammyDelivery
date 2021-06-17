package com.dvm.menu.favorite

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import com.dvm.BaseFragment
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

internal class FavoriteFragment : BaseFragment() {

    private val viewModel: FavoriteViewModel by viewModels()

    @Composable
    override fun Content() {
        ProvideWindowInsets(consumeWindowInsets = false) {
            Favorite(
                state = viewModel.state,
                onEvent = { viewModel.dispatch(it) }
            )
        }
    }
}