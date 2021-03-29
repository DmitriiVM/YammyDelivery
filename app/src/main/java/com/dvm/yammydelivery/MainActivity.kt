package com.dvm.yammydelivery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dvm.appmenu.Navigator
import com.dvm.dish.dish_impl.di.DishComponentHolder
import com.dvm.menu.menu_impl.di.MenuComponentHolder

class MainActivity : AppCompatActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            navigateToMenuScreen()
        }
    }

    override fun navigateToMenuScreen() {
        val dependencies = (application as YammyApplication).appComponent.menuDependencies()
        MenuComponentHolder.init(dependencies)
        MenuComponentHolder.getApi().menuLauncher().launch(
            containerViewId = R.id.fragmentContainer,
            fragmentManager = supportFragmentManager
        )
    }

    override fun navigateToDishScreen(dishId: String) {
        val dependencies = (application as YammyApplication).appComponent.dishDependencies()
        DishComponentHolder.init(dependencies)
        DishComponentHolder.getApi().dishLauncher().launch(
            dishId = dishId,
            containerViewId = R.id.fragmentContainer,
            fragmentManager = supportFragmentManager
        )
    }
}