package com.dvm.menu.api

import android.os.Bundle
import androidx.compose.runtime.Composable
import com.dvm.menu.category.presentation.Category
import com.dvm.menu.favorite.Favorite
import com.dvm.menu.main.Main
import com.dvm.menu.menu.Menu
import com.dvm.menu.search.Search

@Composable
fun MenuScreen() {
    Menu()
}

@Composable
fun SearchScreen() {
    Search()
}

@Composable
fun CategoryScreen(arguments: Bundle?) {
    Category(arguments)
}

@Composable
fun FavoriteScreen() {
    Favorite()
}

@Composable
fun MainScreen() {
    Main()
}