package com.dvm.menu.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dvm.ui.FragmentInsetsComposeView
import com.dvm.ui.YammyDeliveryScreen
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

@AndroidEntryPoint
internal class FavoriteFragment : Fragment() {

    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentInsetsComposeView (requireContext()).apply {

        setContent {
            YammyDeliveryScreen(requireActivity()) {
                ProvideWindowInsets(consumeWindowInsets = false) {
                    Favorite(
                        state = viewModel.state,
                        onEvent = { viewModel.dispatch(it) }
                    )
                }
            }
        }
    }
}