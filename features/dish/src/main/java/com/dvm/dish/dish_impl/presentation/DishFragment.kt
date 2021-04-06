package com.dvm.dish.dish_impl.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dvm.appmenu.Navigator
import com.dvm.dish.dish_impl.Dish
import com.dvm.dish.dish_impl.DishViewModel
import com.dvm.dish.dish_impl.DishViewModelAssistedFactory
import com.dvm.dish.dish_impl.presentation.model.DishNavigationEvent
import com.dvm.ui.themes.YammyDeliveryTheme
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

private const val DISH_ID_KEY = "param1"

@AndroidEntryPoint
internal class DishFragment : Fragment() {

    @Inject
    lateinit var factory: DishViewModelAssistedFactory

    private var dishId: String? = null

    private val viewModel: DishViewModel by viewModels {
        requireNotNull(dishId) { "Dish id can't be null" }
        factory.create(dishId!!)
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