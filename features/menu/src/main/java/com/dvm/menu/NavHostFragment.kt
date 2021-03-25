package com.dvm.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dvm.db.temp.DbGraph

class NavHostFragment: Fragment(R.layout.fragment_nav_host) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DbGraph.provide(requireContext())
    }
}