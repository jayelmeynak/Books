package com.example.books.presentation.navigation

import androidx.compose.runtime.*
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
import com.example.books.presentation.addBook.AddBookScreen
import com.example.books.presentation.mainScreen.MainScreen
import com.example.books.presentation.settings.SettingsScreen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val auth = Firebase.auth
    val user = auth.currentUser

    var previousScreen by remember { mutableStateOf<Screen?>(null) }
    var currentScreen by remember { mutableStateOf<Screen?>(null) }

    val startDestination = if (user != null) MainScreenDataObject(
        uid = user.uid,
        email = user.email ?: ""
    ) else SignInObject

    NavHost(
        navController = navController,
        startDestination = startDestination,
//        enterTransition = {
//            val direction = if (previousScreen != null && currentScreen != null) {
//                if (previousScreen!!.ordinal < currentScreen!!.ordinal) {
//                    AnimatedContentTransitionScope.SlideDirection.Left
//                } else {
//                    AnimatedContentTransitionScope.SlideDirection.Right
//                }
//            } else {
//                AnimatedContentTransitionScope.SlideDirection.Up
//            }
//            Log.d("MyLog", "Enter transition from $previousScreen to $currentScreen with direction $direction")
//            slideIntoContainer(direction, tween(500))
//        },
//        exitTransition = {
//            val direction = if (previousScreen != null && currentScreen != null) {
//                if (previousScreen!!.ordinal < currentScreen!!.ordinal) {
//                    AnimatedContentTransitionScope.SlideDirection.Left
//                } else {
//                    AnimatedContentTransitionScope.SlideDirection.Right
//                }
//            } else {
//                AnimatedContentTransitionScope.SlideDirection.Up
//            }
//            Log.d("MyLog", "Exit transition from $previousScreen to $currentScreen with direction $direction")
//            slideOutOfContainer(direction, tween(500))
//        }
    ) {
        composable<LoginScreenObject> {
            previousScreen = currentScreen
            currentScreen = Screen.LoginScreen
            LoginScreen(navigateToSignInScreen = {
                navController.navigate(SignInObject) {
                    popUpTo(startDestination) { inclusive = true }
                }
            }) { navData ->
                navController.navigate(navData) {
                    popUpTo(startDestination) { inclusive = true }
                }
            }
        }

        composable<SignInObject> {
            previousScreen = currentScreen
            currentScreen = Screen.SignInScreen
            SignInScreen(navigateToLoginScreen = {
                navController.navigate(LoginScreenObject) {
                    popUpTo(startDestination) { inclusive = true }
                }
            }) { navData ->
                navController.navigate(navData) {
                    popUpTo(startDestination) { inclusive = true }
                }
            }
        }

        composable<MainScreenDataObject> { navEntry ->
            previousScreen = currentScreen
            currentScreen = Screen.HomeScreen
            val navData = navEntry.toRoute<MainScreenDataObject>()
            MainScreen(navController = navController) { item ->
                previousScreen = currentScreen
                currentScreen = item
                if (item.route == Screen.ROUTE_HOME) {
                    navController.navigate(navData) {
                        popUpTo(startDestination) {
                            inclusive = true
                        }
                    }
                } else {
                    navController.navigate(item.route) {
                        popUpTo(startDestination) { inclusive = true }
                    }
                }
            }
        }

        composable(Screen.FavoritesScreen.route) {
            previousScreen = currentScreen
            currentScreen = Screen.FavoritesScreen
            FavoriteScreen(navController) { item ->
                previousScreen = currentScreen
                currentScreen = item
                if (item.route == Screen.ROUTE_HOME) {
                    val navData = MainScreenDataObject(
                        uid = user?.uid ?: "",
                        email = user?.email ?: ""
                    )
                    navController.navigate(navData)
                } else {
                    navController.navigate(item.route)
                }
            }
        }

        composable(Screen.SettingsScreen.route) {
            previousScreen = currentScreen
            currentScreen = Screen.SettingsScreen
            SettingsScreen(navController, navigateToItem = { item ->
                previousScreen = currentScreen
                currentScreen = item
                if (item.route == Screen.ROUTE_HOME) {
                    val navData = MainScreenDataObject(
                        uid = user?.uid ?: "",
                        email = user?.email ?: ""
                    )
                    navController.navigate(navData)
                } else {
                    navController.navigate(item.route)
                }
            }) {
                auth.signOut()
                navController.navigate(SignInObject) {
                    popUpTo(startDestination) { inclusive = true }
                }
            }
        }

        composable(Screen.ROUTE_ADD_BOOK) {
            previousScreen = currentScreen
            currentScreen = Screen.AddBookScreen
            AddBookScreen(
                navController = navController,
                navigateToItem = { item ->
                    previousScreen = currentScreen
                    currentScreen = item
                    if (item.route == Screen.ROUTE_HOME) {
                        val navData = MainScreenDataObject(
                            uid = user?.uid ?: "",
                            email = user?.email ?: ""
                        )
                        navController.navigate(navData) {
                            popUpTo(startDestination) { inclusive = true }
                            launchSingleTop = true
                        }
                    } else {
                        navController.navigate(item.route) {
                            popUpTo(startDestination) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },
                onSaved = {
                    previousScreen = currentScreen
                    currentScreen = Screen.HomeScreen
                    navController.navigate(
                        MainScreenDataObject(
                            uid = user?.uid ?: "",
                            email = user?.email ?: ""
                        )
                    ) {
                        popUpTo(startDestination) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}