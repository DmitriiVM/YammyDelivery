package com.dvm.menu.menu.presentation.store

import android.content.Context
import com.dvm.db.AppDatabase
import com.dvm.menu.menu.domain.MenuInteractor
import com.dvm.menu.menu.presentation.store.model.MenuAction
import com.dvm.menu.menu.presentation.store.model.MenuNavigationEvent
import com.dvm.menu.menu.presentation.store.model.MenuResult
import com.dvm.menu.menu.presentation.store.model.MenuSingleEvent
import com.dvm.menu.menu.presentation.ui.model.MenuState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class MenuStore(
    private val context: Context  // TODO temp
) {

    private val _state: MutableStateFlow<MenuState> = MutableStateFlow(MenuState.Loading)
    val state: StateFlow<MenuState> get() = _state

    private val _singleEvent = Channel<MenuSingleEvent>()
    val singleEvent get() = _singleEvent.receiveAsFlow()

    private val _navigationEvent = Channel<MenuNavigationEvent>()
    val navigationEvent get() = _navigationEvent.receiveAsFlow()

    private val action = MutableSharedFlow<MenuAction>()
    private val result = MutableSharedFlow<MenuResult>()


    val categoryDao = AppDatabase.getDb(context).categoryDao()
    val dishDao = AppDatabase.getDb(context).dishDao()
    private val interactor = MenuInteractor(
        categoryDao,
        dishDao
    )


    suspend fun start(scope: CoroutineScope) {
        action
            .onEach { action ->
                when (action) {
                    MenuAction.NavigateToSearch -> _navigationEvent.send(MenuNavigationEvent.NavigateToSearch)
                    MenuAction.OpenAppMenu -> _navigationEvent.send(MenuNavigationEvent.OpenAppMenu)
                    MenuAction.LoadMenu -> getMenu(context)
                    is MenuAction.NavigateToMenuItem -> _navigationEvent.send(
                        MenuNavigationEvent.NavigateToMenuItem(
                            action.id
                        )
                    )
                }
            }
            .launchIn(scope)
        result
            .onEach { result ->
                when (result) {
                    MenuResult.Loading -> _state.value = MenuState.Loading
                    is MenuResult.Data -> _state.value = MenuState.Data(result.items)
                    is MenuResult.Error -> _singleEvent.send(MenuSingleEvent.Error("  Temp  "))  // TODO
                }
            }
            .launchIn(scope)
    }


    private suspend fun getMenu(context: Context) {
        result.emit(MenuResult.Loading)
        try {
            val menuItems = interactor.getParentCategories(context)
            result.emit(MenuResult.Data(menuItems))
        } catch (e: Exception) {
            result.emit(MenuResult.Error(e))
        }
    }

    suspend fun dispatch(action: MenuAction) {
        this.action.emit(action)
    }

}