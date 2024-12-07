package com.example.books.presentation.mainScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.books.presentation.navigation.BottomMenu
import com.example.books.presentation.navigation.Screen
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController, navigateToItem: (Screen) -> Unit) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val viewModel: MainViewModel = viewModel()
    ModalNavigationDrawer(
        modifier = Modifier.fillMaxWidth(),
        drawerState = drawerState,
        drawerContent = {
            Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                DrawerHeader()
                DrawerBody {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    navigateToItem(Screen.AddBookScreen)
                }
            }
        }
    ) {
        if (viewModel.loadingState.value) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                )
            }
        } else {
            Scaffold(
                bottomBar = {
                    BottomMenu(navController) { item ->
                        navigateToItem(item)
                    }
                }
            ) { innerPadding ->
                LazyVerticalGrid(
                    modifier = Modifier.padding(innerPadding),
                    columns = GridCells.Fixed(2)
                ) {
                    items(viewModel.listBooks.value) { book ->
                        BookListItemUi(book = book){ selectedBook ->
                            val route = Screen.EditBookScreen.createRoute(selectedBook)
                            navController.navigate(route)
                        }
                    }
                }
            }
        }
    }
}