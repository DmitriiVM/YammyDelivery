package com.dvm.yammydelivery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.dvm.appmenu.Navigator
import com.dvm.dish.DishFragment
import com.dvm.menu.NavHostFragment
import com.dvm.menu.search.SearchFragment

class MainActivity : AppCompatActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.fragmentContainer, NavHostFragment())
            }
        }
    }

    override fun navigateToDishScreen(dishId: String){
        supportFragmentManager.commit {
            addToBackStack("test")
            replace(R.id.fragmentContainer, DishFragment.newInstance(dishId))
        }
    }

    override fun navigateToMenuScreen() {
        supportFragmentManager.commit {
            addToBackStack("test")
            replace(R.id.fragmentContainer, NavHostFragment())
        }
    }

    override fun navigateToSearchScreen() {
        supportFragmentManager.commit {
            addToBackStack("test")
            replace(R.id.fragmentContainer, SearchFragment())
        }
    }
}