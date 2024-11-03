package com.example.books.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.books.presentation.data.SignInObject
import com.example.books.presentation.navigation.BottomMenu
import com.example.books.presentation.navigation.Screen


@Composable
fun SettingsScreen(
    navController: NavController,
    navigateToItem: (Screen) -> Unit,
    onLogOutClick: (SignInObject) -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomMenu(navController, navigateToItem = { item ->
                navigateToItem(item) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    onLogOutClick(SignInObject)
                }
            ) {

            }
        }
    }
}