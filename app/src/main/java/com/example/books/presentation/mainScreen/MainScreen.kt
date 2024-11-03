package com.example.books.presentation.mainScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.books.presentation.navigation.BottomMenu
import com.example.books.presentation.navigation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController, navigateToItem: (Screen) -> Unit) {
    ModalNavigationDrawer(
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                DrawerHeader()
                DrawerBody()
            }
        }
    ) {
        Scaffold(
            bottomBar ={
                BottomMenu(navController){ item ->
                    navigateToItem(item)
                }
            }
        ) {

        }

    }
}