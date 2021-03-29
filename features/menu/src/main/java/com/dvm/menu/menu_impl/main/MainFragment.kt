package com.dvm.menu.menu_impl.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

internal class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )= ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                Text("Hello Compose!    MENU FRAGMENT")
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val action = MainFragmentDirections.toMenuFragment()
        findNavController().navigate(action)

    }
}


