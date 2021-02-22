package com.dvm.menu.menu.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dvm.menu.menu.presentation.store.MenuStore
import com.dvm.menu.menu.presentation.store.model.MenuAction
import com.dvm.menu.menu.presentation.store.toAction
import com.dvm.menu.menu.presentation.ui.model.MenuIntent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MenuViewModel(app: Application) : AndroidViewModel(app) {

    private val store = MenuStore()

    init {
        viewModelScope.launch {
            store.start(app.applicationContext, viewModelScope)
            store
                .navigationEvent
                .onEach {
                    Log.d("mmm", "MenuViewModel :   -+-  ")
                }
                .launchIn(viewModelScope)

            store.dispatch(MenuAction.LoadMenu)
        }
    }

    fun dispatch(intent: MenuIntent) {
        viewModelScope.launch {
            store.dispatch(intent.toAction())
        }
    }

    fun state() = store.state
}