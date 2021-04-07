package com.dvm.menu.menu_impl.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.dvm.navigation.Destination
import com.dvm.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class MainFragment : Fragment() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    )= ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                Text("Hello Compose!    Main FRAGMENT")

                Box(contentAlignment = Alignment.Center) {
                    Button(onClick = { navigator.navigationTo?.invoke(Destination.Menu) }) {

                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        navigator.navigationTo?.invoke(Destination.Menu)
    }
}


