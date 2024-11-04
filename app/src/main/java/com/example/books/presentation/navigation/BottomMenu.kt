package com.example.books.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController

@Composable
fun BottomMenu(navController: NavController, navigateToItem: (Screen) -> Unit){
    val items = listOf(
        Screen.HomeScreen,
        Screen.FavoritesScreen,
        Screen.SettingsScreen
    )
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    val selectedItem = remember { mutableStateOf(currentRoute) }
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedItem.value == item.route,
                onClick = {
                    if(selectedItem.value != item.route) {
                        selectedItem.value = item.route
                        navigateToItem(item)
                    }
                },
                icon = {
                    Icon(painter = painterResource(id = item.icon), contentDescription = item.title)
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}