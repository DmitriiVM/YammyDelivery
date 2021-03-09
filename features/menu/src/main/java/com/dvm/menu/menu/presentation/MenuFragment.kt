package com.dvm.menu.menu.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dvm.db.AppDatabase
import com.dvm.menu.menu.domain.MenuInteractor
import com.dvm.menu.menu.presentation.store.model.MenuNavigationEvent
import com.dvm.menu.menu.presentation.ui.model.MenuIntent
import com.dvm.menu.menu.presentation.ui.model.MenuState
import com.dvm.ui.themes.YammyDeliveryTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class MenuFragment : Fragment() {

    private val model: MenuViewModel by viewModels()

    @ExperimentalStdlibApi
    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {

        lifecycleScope.launch {

            // TODO remove
            val items = MenuInteractor(
                AppDatabase.getDb(context).categoryDao(),
                AppDatabase.getDb(context).dishDao()
            )
                .getParentCategories(context)

            setContent {
                YammyDeliveryTheme(
                    requireActivity().window
                ) {
                    ProvideWindowInsets(consumeWindowInsets = false){
                        val menuItems = remember { items }
                        MenuView(
                            menuItems = menuItems,
                            onItemClick = { model.dispatch(MenuIntent.MenuItemClick(it)) },
                            onSearchClick = { model.dispatch(MenuIntent.SearchClick) },
                            onAppMenuClick = { model.dispatch(MenuIntent.AppMenuClick) }
                        )
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        navigateToMenuItem("5ed8da011f071c00465b1fe4")


        model
            .state()
            .onEach {
                when (it) {
                    MenuState.Loading -> Log.d("mmm", "MenuFragment :  onViewCreated --  $it")
                    is MenuState.Data -> Log.d(
                        "mmm",
                        "MenuFragment :  onViewCreated --  ${it.items}"
                    )
                }

            }
            .launchIn(lifecycleScope)

        model
            .navigation()
            .onEach { navigation ->
                when (navigation) {
                    is MenuNavigationEvent.NavigateToMenuItem -> navigateToMenuItem(navigation.id)
                    MenuNavigationEvent.NavigateToSearch -> navigateToSearch()
                    MenuNavigationEvent.OpenAppMenu -> openAppMenu()
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun openAppMenu() {

    }

    private fun navigateToSearch() {
        findNavController().navigate(MenuFragmentDirections.toSearchFragment())
    }

    private fun navigateToMenuItem(id: String) {
        findNavController().navigate(MenuFragmentDirections.toCategoryFragment(id))
    }
}
