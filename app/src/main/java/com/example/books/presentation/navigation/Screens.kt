package com.example.books.presentation.navigation

import android.net.Uri
import com.example.books.R
import com.example.books.domain.Book
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

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
    data class EditBookScreen(val bookJson: String) :
        Screen("Edit Book", "editBook?book={bookJson}", R.drawable.ic_edit) {

        companion object {
            fun createRoute(book: Book): String {
                val newBook = book.copy(imageUrl = Uri.encode(book.imageUrl))
                val json = Uri.encode(Json.encodeToString(newBook))
                return "editBook?book=$json"
            }
        }
    }


    companion object {
        const val ROUTE_HOME = "home"
        const val ROUTE_LOGIN = "login"
        const val ROUTE_SIGN_IN = "signIn"
        const val ROUTE_FAVORITE = "favorites"
        const val ROUTE_SETTINGS = "settings"
        const val ROUTE_ADD_BOOK = "addBook"
    }
}