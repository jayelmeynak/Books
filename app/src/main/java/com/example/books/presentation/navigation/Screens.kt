package com.example.books.presentation.navigation

import com.example.books.R

sealed class Screen(
    val title: String,
    val route: String,
    val icon: Int
) {
    object LoginScreen : Screen("Login", ROUTE_LOGIN, R.drawable.ic_login)
    object SignInScreen : Screen("Login", ROUTE_SIGN_IN, R.drawable.ic_login)
    object HomeScreen : Screen("Home", ROUTE_HOME, R.drawable.ic_home)
    object FavoritesScreen : Screen("Favorites", ROUTE_FAVORITE, R.drawable.ic_favorite)
    object AddBookScreen : Screen("AddBook", ROUTE_ADD_BOOK, R.drawable.ic_add_book)
    object SettingsScreen : Screen("Settings", ROUTE_SETTINGS, R.drawable.ic_settings)


    companion object {
        const val ROUTE_HOME = "home"
        const val ROUTE_LOGIN = "login"
        const val ROUTE_SIGN_IN = "signIn"
        const val ROUTE_FAVORITE = "favorites"
        const val ROUTE_SETTINGS = "settings"
        const val ROUTE_ADD_BOOK = "addBook"
    }
}