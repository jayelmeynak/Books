package com.example.books.presentation.mainScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.books.domain.Book
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainViewModel: ViewModel() {

    val listBooks = mutableStateOf(emptyList<Book>())
    val loadingState = mutableStateOf(false)

    init {
        loadingState.value = true
        getBooks {
            listBooks.value = it
        }
    }



    fun isAdmin(onAdmin: (Boolean) -> Unit) {
        val uid = Firebase.auth.currentUser?.uid ?: ""
        Firebase.firestore.collection("admin").document(uid).get().addOnSuccessListener {
            onAdmin(it.get("isAdmin") as Boolean)
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