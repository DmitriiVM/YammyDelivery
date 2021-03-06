package com.dvm.menu

import android.os.Bundle
import androidx.fragment.app.Fragment

class NavHostFragment: Fragment(R.layout.fragment_nav_host) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Graph.provide(requireContext())
    }
}