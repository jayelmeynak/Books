package com.example.books.presentation.mainScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.books.domain.Book
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainViewModel : ViewModel() {

    val listBooks = mutableStateOf(emptyList<Book>())
    val loadingState = mutableStateOf(false)
    val isAdminState = mutableStateOf(false)

    init {
        loadingState.value = true
        getBooks {
            listBooks.value = it
        }
        checkAdminStatus()
    }

    private fun checkAdminStatus() {
        val uid = Firebase.auth.currentUser?.uid ?: return
        Firebase.firestore.collection("admin")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                isAdminState.value = document.get("isAdmin") as? Boolean ?: false
            }
            .addOnFailureListener {
                isAdminState.value = false
            }
    }

    fun getBooks(onBooks: (List<Book>) -> Unit) {
        Firebase.firestore.collection("books").get().addOnSuccessListener {
            val books = it.toObjects(Book::class.java)
            onBooks(books)
            loadingState.value = false
        }
    }
}