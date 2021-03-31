package com.dvm.yammydelivery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dvm.appmenu.Navigator
import com.dvm.auth.auth_impl.di.AuthComponentHolder
import com.dvm.dish.dish_impl.di.DishComponentHolder
import com.dvm.menu.menu_impl.di.MenuComponentHolder

class MainActivity : AppCompatActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            navigateToAuthScreen()
        }
    }

    override fun navigateToMenuScreen() {
        MenuComponentHolder.init()
        MenuComponentHolder.getApi().menuLauncher().launch(
            containerViewId = R.id.fragmentContainer,
            fragmentManager = supportFragmentManager
        )
    }

    override fun navigateToDishScreen(dishId: String) {
        DishComponentHolder.init()
        DishComponentHolder.getApi().dishLauncher().launch(
            dishId = dishId,
            containerViewId = R.id.fragmentContainer,
            fragmentManager = supportFragmentManager
        )
    }

    fun navigateToAuthScreen(){
        AuthComponentHolder.init()
        AuthComponentHolder.getApi().authLauncher().launch(
            containerViewId = R.id.fragmentContainer,
            fragmentManager = supportFragmentManager
        )
    }
}