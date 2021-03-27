package com.dvm.yammydelivery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.dvm.appmenu.Navigator
import com.dvm.dish.DishFragment
import com.dvm.dish.di.DishComponentHolder
import com.dvm.menu.NavHostFragment
import com.dvm.menu.di.MenuComponentHolder
import com.dvm.menu.search.SearchFragment

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
        supportFragmentManager.commit {
            addToBackStack("test")
            replace(R.id.fragmentContainer, NavHostFragment())
        }
    }

    override fun navigateToDishScreen(dishId: String) {
        val dependencies = (application as YammyApplication).appComponent.dishDependencies()
        DishComponentHolder.init(dependencies)
        supportFragmentManager.commit {
            addToBackStack("test")
            replace(R.id.fragmentContainer, DishFragment.newInstance(dishId))
        }
    }

    override fun navigateToSearchScreen() {
        supportFragmentManager.commit {
            addToBackStack("test")
            replace(R.id.fragmentContainer, SearchFragment())
        }
    }
}