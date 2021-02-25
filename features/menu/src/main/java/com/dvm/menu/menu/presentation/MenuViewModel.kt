package com.dvm.menu.menu.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.menu.menu.presentation.store.MenuStore
import com.dvm.menu.menu.presentation.store.model.MenuAction
import com.dvm.menu.menu.presentation.store.toAction
import com.dvm.menu.menu.presentation.ui.model.MenuIntent
import kotlinx.coroutines.launch

class MenuViewModel(app: Application) : AndroidViewModel(app) {  // TODO

    private val store = MenuStore(app.applicationContext)

    init {
        viewModelScope.launch {
            store.start(viewModelScope)
            store.dispatch(MenuAction.LoadMenu)
        }
    }

    fun dispatch(intent: MenuIntent) {
        viewModelScope.launch {
            store.dispatch(intent.toAction())
        }
    }

    fun state() = store.state
    fun navigation() = store.navigationEvent
}