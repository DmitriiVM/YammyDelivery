package com.dvm.menu.menu_impl.launcher

import android.content.Context
import androidx.fragment.app.Fragment
import com.dvm.menu.R
import com.dvm.menu.menu_impl.di.MenuComponentHolder

internal class NavHostFragment: Fragment(R.layout.fragment_nav_host) {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        MenuComponentHolder.init()
    }
}