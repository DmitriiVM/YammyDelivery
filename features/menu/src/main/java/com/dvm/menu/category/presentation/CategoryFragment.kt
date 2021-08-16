package com.dvm.menu.category.presentation

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import com.dvm.BaseFragment
import com.google.accompanist.insets.ProvideWindowInsets

internal class CategoryFragment : BaseFragment() {

    private val viewModel: CategoryViewModel by viewModels()

    @Composable
    override fun Content() {
        ProvideWindowInsets(consumeWindowInsets = false) {
            Category(
                state = viewModel.state,
                onEvent = { viewModel.dispatch(it) }
            )
        }
    }
}