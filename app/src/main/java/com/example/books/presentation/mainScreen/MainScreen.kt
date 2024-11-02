package com.example.books.presentation.mainScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainScreen(innerPadding: PaddingValues) {
    ModalNavigationDrawer(
        modifier = Modifier.fillMaxWidth(0.7f),
        drawerContent = {
            Column(modifier = Modifier.fillMaxSize()) {
                DrawerHeader()
                DrawerBody()
            }
        }
    ) {

    }
}