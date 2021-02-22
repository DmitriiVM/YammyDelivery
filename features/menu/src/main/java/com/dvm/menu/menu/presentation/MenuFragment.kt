package com.dvm.menu.menu.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dvm.menu.menu.domain.model.MenuItem
import com.dvm.menu.menu.presentation.ui.model.MenuIntent
import com.dvm.menu.menu.presentation.ui.model.MenuState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class MenuFragment : Fragment() {

    val model: MenuViewModel by viewModels()

    @ExperimentalStdlibApi
    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        lifecycleScope.launch {
            val items = emptyList<MenuItem>()
//            val items = MenuInteractor().getParentCategories(context)

            setContent {
                MaterialTheme {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        model
            .state()
            .onEach {
                when (it){
                    MenuState.Loading -> Log.d("mmm", "MenuFragment :  onViewCreated --  $it")
                    is MenuState.Data -> Log.d("mmm", "MenuFragment :  onViewCreated --  ${it.items}")
                }

            }
            .launchIn(lifecycleScope)
    }












}
