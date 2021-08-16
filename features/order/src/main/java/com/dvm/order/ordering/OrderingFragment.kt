package com.dvm.order.ordering

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dvm.BaseFragment
import com.dvm.navigation.api.model.MAP_ADDRESS
import com.google.accompanist.insets.ProvideWindowInsets

internal class OrderingFragment : BaseFragment() {

    private val viewModel: OrderingViewModel by viewModels()

    @Composable
    override fun Content() {
        ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
            Ordering(
                state = viewModel.state,
                onEvent = { viewModel.dispatchEvent(it) }
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findNavController()
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<String>(MAP_ADDRESS)
            ?.observe(viewLifecycleOwner) { address ->
                viewModel.setMapAddress(address)
            }
    }
}