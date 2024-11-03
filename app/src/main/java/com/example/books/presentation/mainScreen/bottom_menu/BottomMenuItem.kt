package com.example.books.presentation.mainScreen.bottom_menu

import com.example.books.R

sealed class BottomMenuItem(
    val title: String,
    val route: String,
    val icon: Int
) {
    object Home : BottomMenuItem("Home", "home", R.drawable.ic_home)
    object Favorites : BottomMenuItem("Favorites", "favorites", R.drawable.ic_favorite)
    object Settings : BottomMenuItem("Settings", "settings", R.drawable.ic_settings)
}
