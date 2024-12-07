package com.example.books.presentation.addBook

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.books.domain.Book
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class AddEditBookViewModel : ViewModel() {
    val bookTitle = mutableStateOf("")
    val bookDescription = mutableStateOf("")
    val bookPrice = mutableStateOf("")
    val selectImageUri = mutableStateOf<Uri?>(null)
    val imageUrl = mutableStateOf("")
    val selectedCategory = mutableStateOf("Click to select a category")
    private val storage = Firebase.storage
    private val firestore = Firebase.firestore
    val errorMessage = mutableStateOf("")
    val loadingState = mutableStateOf(false)
    val isEditState = mutableStateOf(false)
    val isInitialized = mutableStateOf(false)
    val keyBook = mutableStateOf("")

    fun initialize(book: Book?) {
        if (book != null) {
            bookTitle.value = book.title
            bookDescription.value = book.description
            bookPrice.value = book.price
            selectedCategory.value = book.category
            imageUrl.value = book.imageUrl
            keyBook.value = book.key
            isEditState.value = true
        } else {
            bookTitle.value = ""
            bookDescription.value = ""
            bookPrice.value = ""
            selectedCategory.value = ""
            imageUrl.value = ""
            keyBook.value = ""
            isEditState.value = false
        }
        isInitialized.value = true
    }


    fun saveImageUri(onSuccess: () -> Unit) {
        loadingState.value = true
        val timeStamp = System.currentTimeMillis()
        val storageRef = storage.reference
            .child("book_images")
            .child("images_$timeStamp.jpg")
        if (selectImageUri.value == null && !isEditState.value) {
            errorMessage.value = "Please select an image"
            if (loadingState.value) loadingState.value = false
            return
        }
        if (isEditState.value && selectImageUri.value == null) {
            saveBook(imageUrl.value) {
                loadingState.value = false
                onSuccess()
            }
            errorMessage.value = ""
            return
        }
        errorMessage.value = ""
        val uri = selectImageUri.value!!
        val uploadTask = storageRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { url ->
                saveBook(url.toString()) {
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
        if (bookTitle.value.isEmpty() || bookDescription.value.isEmpty() || bookPrice.value.isEmpty() || selectedCategory.value.isEmpty() || selectedCategory.value == "Click to select a category") {
            errorMessage.value = "Please fill all fields"
            if (loadingState.value) loadingState.value = false
            return
        }
        val title = bookTitle.value
        val description = bookDescription.value
        val price = bookPrice.value
        val category = selectedCategory.value
        val db = firestore.collection("books")
        val key = if(isEditState.value) keyBook.value else db.document().id
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