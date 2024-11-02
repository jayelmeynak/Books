package com.example.books.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.books.presentation.login.LoginScreen
import com.example.books.presentation.login.SignInScreen
import com.example.books.presentation.mainScreen.MainScreen
import com.example.books.ui.theme.BooksTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BooksTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val auth = Firebase.auth
//                    if (auth.currentUser == null){
//                        SignInScreen(innerPadding)
//                    }
                    MainScreen(innerPadding)
                }
            }
        }
    }
}
