package com.dvm.menu.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dvm.appmenu.Drawer
import com.dvm.menu.search.model.SearchEvent
import com.dvm.menu.search.model.SearchState
import com.dvm.navigation.Navigator
import com.dvm.ui.components.AppBarIconBack
import com.dvm.ui.components.TransparentAppBar
import dev.chrisbanes.accompanist.insets.statusBarsHeight

@Composable
internal fun Search(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit,
    navigator: Navigator,
) {
    Drawer(navigator = navigator) {


        Column(Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.statusBarsHeight())
            TransparentAppBar(
                navigationIcon = {

                    AppBarIconBack(onNavigateUp = { onEvent(SearchEvent.NavigateUp) })
                }
            ) {
                OutlinedTextField(
                    value = state.query,
                    onValueChange = { onEvent(SearchEvent.QueryChange(it)) },
                    modifier = Modifier
                )
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = null
                    )
                }
            }
            Box(Modifier.fillMaxSize()) {
//                SearchResult()

                if (state.query.trim().isEmpty()) {
                    LazyColumn(Modifier.fillMaxWidth()) {
                        items(state.hints) { hint ->
                            HintItem(
                                hint = hint,
                                onHintClick = {
                                    onEvent(SearchEvent.HintClick(hint))
                                },
                                onRemoveHintClick = {
                                    onEvent(SearchEvent.RemoveHintClick(hint))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HintItem(
    hint: String,
    onHintClick: (String) -> Unit,
    onRemoveHintClick: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .clickable { onHintClick(hint) }
    ) {
        Text(text = hint, modifier = Modifier)
        IconButton(onClick = { onRemoveHintClick(hint) }, modifier = Modifier) {
            Icon(
                imageVector = Icons.Default.Cancel,
                contentDescription = null
            )
        }
    }
}
