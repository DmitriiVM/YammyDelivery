package com.dvm.menu.api

import android.os.Bundle
import androidx.compose.runtime.Composable
import com.dvm.menu.category.presentation.CategoryScreen
import com.dvm.menu.favorite.FavoriteScreen
import com.dvm.menu.main.MainScreen
import com.dvm.menu.menu.MenuScreen
import com.dvm.menu.search.SearchScreen

@Composable
fun Menu() {
    MenuScreen()
}

@Composable
fun Search() {
    SearchScreen()
}

@Composable
fun Category(arguments: Bundle?) {
    CategoryScreen(arguments)
}

@Composable
fun Favorite() {
    FavoriteScreen()
}

@Composable
fun Main() {
    MainScreen()
}