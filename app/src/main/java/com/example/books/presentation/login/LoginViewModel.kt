package com.example.books.presentation.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginViewModel : ViewModel() {
    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val confirmPassword = mutableStateOf("")
    val emailCorrect = mutableStateOf(true)
    val passwordCorrect = mutableStateOf(true)
    val confirmPasswordCorrect = mutableStateOf(true)
    val auth = Firebase.auth
    val errorMessage = mutableStateOf("")

    fun validateEmail() {
        emailCorrect.value =
            email.value.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email.value)
                .matches()
    }

    fun validatePassword() {
        passwordCorrect.value = password.value.isNotBlank() && password.value.length in 6..24
    }

    fun validateConfirmPassword() {
        confirmPasswordCorrect.value =
            confirmPassword.value.isNotBlank() && confirmPassword.value == password.value
    }

    fun validateFormForLogin(): Boolean {
        validateEmail()
        validatePassword()
        validateConfirmPassword()
        return emailCorrect.value && passwordCorrect.value && confirmPasswordCorrect.value
    }

    fun validateFormForSignIn(): Boolean {
        validateEmail()
        validatePassword()
        return emailCorrect.value && passwordCorrect.value
    }

    fun createAccount() {
        if (validateFormForLogin()) {
            auth.createUserWithEmailAndPassword(email.value, password.value)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("MyLog", "Account created")
                    } else {
                        Log.d("MyLog", "Account creation failed: ${task.exception?.message}")
                        errorMessage.value = task.exception?.message ?: "Unknown error"
                    }
                }
        }
    }

    fun signIn() {
        if (validateFormForSignIn()) {
            auth.signInWithEmailAndPassword(email.value, password.value)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("MyLog", "Sign in successful")
                    } else {
                        Log.d("MyLog", "Sign in failed: ${task.exception?.message}")
                        errorMessage.value = task.exception?.message ?: "Unknown error"
                    }
                }
        }
    }
}
