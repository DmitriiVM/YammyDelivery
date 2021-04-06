package com.dvm.yammydelivery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dvm.appmenu.Navigator
import com.dvm.auth.auth_api.AuthLauncher
import com.dvm.dish.dish_api.DishLauncher
import com.dvm.menu.menu_api.MenuLauncher
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Navigator {

    @Inject
    lateinit var authLauncher: AuthLauncher

    @Inject
    lateinit var menuLauncher: MenuLauncher

    @Inject
    lateinit var dishLauncher: DishLauncher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            navigateToAuthScreen()
        }
    }

    override fun navigateToMenuScreen() {
        menuLauncher.launch(
            containerViewId = R.id.fragmentContainer,
            fragmentManager = supportFragmentManager
        )
    }

    override fun navigateToDishScreen(dishId: String) {
        dishLauncher.launch(
            dishId = dishId,
            containerViewId = R.id.fragmentContainer,
            fragmentManager = supportFragmentManager
        )
    }

    fun navigateToAuthScreen() {
        authLauncher.launch(
            containerViewId = R.id.fragmentContainer,
            fragmentManager = supportFragmentManager
        )
    }
}