package com.dvm.menu.menu_impl.category.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dvm.appmenu.Navigator
import com.dvm.menu.menu_impl.category.presentation.model.CategoryNavigationEvent
import com.dvm.menu.menu_impl.di.MenuComponentHolder
import com.dvm.ui.themes.YammyDeliveryTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class CategoryFragment : Fragment() {

    @Inject
    lateinit var factory: CategoryViewModelAssistedFactory

    private val args: CategoryFragmentArgs by navArgs()

    private val viewModel: CategoryViewModel by viewModels {
        factory.create(args.id)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MenuComponentHolder.getComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel
            .navigationEvent
            .onEach { event ->
                when (event) {
                    is CategoryNavigationEvent.ToDetails -> navigateToDetails(event.dishId)
                    CategoryNavigationEvent.Up -> navigateUp()
                }
            }
            .launchIn(lifecycleScope)
    }

    @ExperimentalFoundationApi
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
                    Category(
                        state = viewModel.state,
                        onEvent = { viewModel.dispatch(it) },
                        navigator = requireActivity() as Navigator
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navigateUp()
                }
            }
        )
    }

    private fun navigateToDetails(dishId: String) {
        (requireActivity() as Navigator).navigateToDishScreen(dishId)
    }

    private fun navigateUp() {
        findNavController().navigateUp()
    }
}