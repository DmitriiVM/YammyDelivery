package com.dvm.menu.search

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import com.dvm.BaseFragment
import com.google.accompanist.insets.ProvideWindowInsets

internal class SearchFragment : BaseFragment() {

    private val viewModel: SearchViewModel by viewModels()

    @Composable
    override fun Content() {
        ProvideWindowInsets(consumeWindowInsets = false) {
            Search(
                state = viewModel.state,
                onEvent = { viewModel.dispatch(it) }
            )
        }
    }
}