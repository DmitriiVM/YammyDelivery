package com.dvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.fragment.app.Fragment
import com.dvm.ui.FragmentInsetsComposeView
import com.dvm.ui.YammyDeliveryScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    @Composable
    abstract fun Content()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentInsetsComposeView(requireContext()).apply {
        setContent {
            YammyDeliveryScreen(requireActivity()) {
                Content()
            }
        }
    }
}