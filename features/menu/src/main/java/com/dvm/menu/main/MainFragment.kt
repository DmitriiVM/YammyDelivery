package com.dvm.menu.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.dvm.menu.R

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(
        R.layout.fragment_main, container, false
    ).apply {
        findViewById<ComposeView>(R.id.compose_view).setContent {
            MaterialTheme {
                Text("Hello Compose lala!")
            }
        }
    }
}


