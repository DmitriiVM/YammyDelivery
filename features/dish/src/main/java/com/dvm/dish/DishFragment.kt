package com.dvm.dish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dvm.appmenu.Navigator
import com.dvm.dish.model.DishNavigationEvent
import com.dvm.ui.themes.YammyDeliveryTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val DISH_ID_KEY = "param1"

class DishFragment : Fragment() {

    private var dishId: String? = null

    private val viewModel: DishViewModel by viewModels {
        requireNotNull(dishId) { "Dish id can't be null" }
        CategoryViewModelFactory(dishId!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dishId = it.getString(DISH_ID_KEY)
        }
        viewModel
            .navigationEvent
            .onEach {event ->
                if (event is DishNavigationEvent.Up){
                    navigateUp()
                }
            }
            .launchIn(lifecycleScope)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            YammyDeliveryTheme(
                requireActivity().window
            ) {
                ProvideWindowInsets(consumeWindowInsets = false) {
                    viewModel.state?.let { state ->
                        Dish(
                            state = state,
                            onEvent = { viewModel.dispatchEvent(it) },
                            navigator = requireActivity() as Navigator
                        )
                    }
                }
            }
        }
    }

    private fun navigateUp() {
        requireActivity().onBackPressed()
    }

    companion object {
        @JvmStatic
        fun newInstance(dishId: String) =
            DishFragment().apply {
                arguments = Bundle().apply {
                    putString(DISH_ID_KEY, dishId)
                }
            }
    }
}