package com.example.books.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.books.presentation.data.LoginScreenObject
import com.example.books.presentation.data.MainScreenDataObject
import com.example.books.presentation.data.SignInObject
import com.example.books.presentation.favorite.FavoriteScreen
import com.example.books.presentation.login.LoginScreen
import com.example.books.presentation.login.SignInScreen
import com.example.books.presentation.mainScreen.MainScreen
import com.example.books.presentation.settings.SettingsScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val auth = Firebase.auth
    val user = auth.currentUser

    val startDestination = if (user != null) MainScreenDataObject(
        uid = user.uid,
        email = user.email ?: ""
    ) else SignInObject


    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<LoginScreenObject> {
            LoginScreen() { navData ->
                navController.navigate(navData) {
                    popUpTo(startDestination) { inclusive = true }
                }
            }
        }

        composable<SignInObject> {
            SignInScreen(navigateToLoginScreen = {
                navController.navigate(LoginScreenObject)
            }) { navData ->
                navController.navigate(navData) {
                    popUpTo(startDestination) { inclusive = true }
                }
            }
        }

        composable<MainScreenDataObject> { navEntry ->
            val navData = navEntry.toRoute<MainScreenDataObject>()
            MainScreen(navController = navController){ item ->
                if (item.route == Screen.ROUTE_HOME) {
                    navController.navigate(navData)
                }
                else{
                    navController.navigate(item.route)
                }
            }
        }

        composable(Screen.FavoritesScreen.route) {
            FavoriteScreen(navController) { item ->
                if (item.route == Screen.ROUTE_HOME) {
                    val navData = MainScreenDataObject(
                        uid = user?.uid ?: "",
                        email = user?.email ?: ""
                    )
                    navController.navigate(navData)
                }
                else{
                    navController.navigate(item.route)
                }
            }
        }

        composable(Screen.SettingsScreen.route) {
            SettingsScreen(navController, navigateToItem = { item ->
                if (item.route == Screen.ROUTE_HOME) {
                    val navData = MainScreenDataObject(
                        uid = user?.uid ?: "",
                        email = user?.email ?: ""
                    )
                    navController.navigate(navData)
                }
                else{
                    navController.navigate(item.route)
                }
            }) {
                auth.signOut()
                navController.navigate(SignInObject) {
                    popUpTo(startDestination) { inclusive = true }
                }
            }
        }
    }
}