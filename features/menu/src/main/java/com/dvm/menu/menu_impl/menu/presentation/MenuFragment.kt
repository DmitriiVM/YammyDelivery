package com.dvm.menu.menu_impl.menu.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dvm.appmenu.Navigator
import com.dvm.menu.menu_impl.menu.presentation.model.MenuNavigationEvent
import com.dvm.ui.themes.YammyDeliveryTheme
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
internal class MenuFragment : Fragment() {

    @Inject
    lateinit var factory: MenuViewModelFactory

    private val model: MenuViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model
            .navigationEvent
            .onEach { navigation ->
                when (navigation) {
                    is MenuNavigationEvent.NavigateToCategory -> navigateToCategory(navigation.id)
                    MenuNavigationEvent.NavigateToSearch -> navigateToSearch()
                    MenuNavigationEvent.OpenAppMenu -> openAppMenu()
                }
            }
            .launchIn(lifecycleScope)
    }

    @ExperimentalStdlibApi
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
                    MenuView(
                        menuItems = model.menuItems,
                        onEvent = { model.dispatch(it) },
                        navigator = requireActivity() as Navigator
                    )
                }
            }
        }
    }

    private fun openAppMenu() {

    }

    private fun navigateToSearch() {
        findNavController().navigate(MenuFragmentDirections.toSearchFragment())
    }

    private fun navigateToCategory(id: String) {
        findNavController().navigate(MenuFragmentDirections.toCategoryFragment(id))
    }
}
