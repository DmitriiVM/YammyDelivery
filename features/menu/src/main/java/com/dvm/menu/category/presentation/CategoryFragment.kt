package com.dvm.menu.category.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dvm.menu.category.temp.CategoryIntent
import com.dvm.menu.category.temp.CategoryState
import com.dvm.menu.category.temp.SortType
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CategoryFragment : Fragment() {

    private val args: CategoryFragmentArgs by navArgs()
    private val viewModel: CategoryViewModel by viewModels()

    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {


        setContent {
            MaterialTheme {

                val state by viewModel.state.collectAsState()
                val currentState = remember(state) { state }

                Column {
                    TopAppBar(
                        title = { Text(text = "Dishes") },
                        navigationIcon = {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier.clickable(
                                    onClick = { findNavController().navigateUp() }
                                )
                            )
                        },
                        actions = {
                            DropdownMenu(
                                expanded = currentState is CategoryState.Data && currentState.showSort,
                                onDismissRequest = { viewModel.dispatch(CategoryIntent.SortClick(false))}) {

                                SortType.values().forEach {
                                    DropdownMenuItem(onClick = {
                                        Log.d("mmm", "CategoryFragment :  onCreateView --  +++")
                                        viewModel.dispatch(CategoryIntent.SortPick(it))
                                    }) {
                                        Text(text = it.title)
                                    }
                                }


                            }

                            Icon(
                                imageVector = Icons.Outlined.Sort,
                                contentDescription = null,
                                modifier = Modifier.clickable(
                                    onClick = {
                                        viewModel.dispatch(CategoryIntent.SortClick(true))
                                    }
                                )
                            )


                        }
                    )


                    when (state) {
                        is CategoryState.Data -> {

                            Column {
                                val data = remember(state) { state as CategoryState.Data }
                                if (data.subcategories.isNotEmpty()) {
                                    ScrollableTabRow(selectedTabIndex = 0, modifier = Modifier) {
                                        data.subcategories.forEach {
                                            Tab(
                                                selected = true,
                                                onClick = {
                                                    viewModel.dispatch(
                                                        CategoryIntent.SubcategoryClick(it.id)
                                                    )
                                                }) {
                                                Text(text = it.name)
                                            }
                                        }
                                    }
                                }

                                LazyVerticalGrid(cells = GridCells.Fixed(2)) {
                                    items(data.dishes) {
                                        Text(
                                            text = it.name,
                                            modifier = Modifier.padding(top = 20.dp)
                                        )
                                        Text(
                                            text = it.rating.toString(),
                                            modifier = Modifier.padding(bottom = 20.dp)
                                        )
                                    }
                                }
                            }

                        }
                        is CategoryState.Error -> {
                        }
                        CategoryState.Loading -> {
                        }
                    }

                }


            }
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        viewModel.loadContent(args.id)


        viewModel
            .state
            .onEach {
                Log.d("mmm", "CategoryFragment :  onViewCreated --  $it")
            }
            .launchIn(lifecycleScope)



        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }
            }
        )
    }


}