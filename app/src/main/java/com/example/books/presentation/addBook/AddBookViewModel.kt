package com.example.books.presentation.addBook

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.books.domain.Book
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class AddBookViewModel : ViewModel() {
    val bookTitle = mutableStateOf("")
    val bookDescription = mutableStateOf("")
    val bookPrice = mutableStateOf("")
    val selectImageUri = mutableStateOf<Uri?>(null)
    val selectedCategory = mutableStateOf("")
    private val storage = Firebase.storage
    private val firestore = Firebase.firestore
    val errorMessage = mutableStateOf("")
    val loadingState = mutableStateOf(false)


    fun saveImageUri(onSuccess: () -> Unit) {
        loadingState.value = true
        val timeStamp = System.currentTimeMillis()
        val storageRef = storage.reference
            .child("book_images")
            .child("images_$timeStamp.jpg")
        if (selectImageUri.value == null) {
            errorMessage.value = "Please select an image"
            if(loadingState.value) loadingState.value = false
            return
        }
        errorMessage.value = ""
        val uri = selectImageUri.value!!
        val uploadTask = storageRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { url ->
                saveBook(url.toString()){
                    loadingState.value = false
                    onSuccess()
                }
            }
        }
    }

    private fun saveBook(
        url: String,
        onSuccess: () -> Unit
    ) {
        if (bookTitle.value.isEmpty() || bookDescription.value.isEmpty() || bookPrice.value.isEmpty() || selectedCategory.value.isEmpty()) {
            errorMessage.value = "Please fill all fields"
            if (loadingState.value) loadingState.value = false
            return
        }
        val title = bookTitle.value
        val description = bookDescription.value
        val price = bookPrice.value
        val category = selectedCategory.value
        val db = firestore.collection("books")
        val key = db.document().id
        val book = Book(
            key = key,
            title = title,
            description = description,
            price = price,
            category = category,
            imageUrl = url
        )
        db.document(key).set(book)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                errorMessage.value = "Failed to save book: ${exception.message}"
            }

    }

    fun setImageUri(uri: Uri?) {
        selectImageUri.value = uri
    }

    fun setCategory(category: String) {
        selectedCategory.value = category
    }

    fun setBookTitle(title: String) {
        bookTitle.value = title
    }

    fun setBookDescription(description: String) {
        bookDescription.value = description
    }

    fun setBookPrice(price: String) {
        bookPrice.value = price
    }
}