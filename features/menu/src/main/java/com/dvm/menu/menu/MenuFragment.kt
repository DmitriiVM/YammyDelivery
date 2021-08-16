package com.dvm.menu.menu

import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import com.dvm.BaseFragment
import com.google.accompanist.insets.ProvideWindowInsets

internal class MenuFragment : BaseFragment() {

    private val viewModel: MenuViewModel by viewModels()

    @Composable
    override fun Content() {
        ProvideWindowInsets(consumeWindowInsets = false) {
            MenuView(
                menuItems = viewModel.menuItems,
                onEvent = { viewModel.dispatch(it) }
            )
        }
    }
}
