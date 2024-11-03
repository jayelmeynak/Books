package com.example.books.presentation.data

import kotlinx.serialization.Serializable

@Serializable
data class MainScreenDataObject(
    val email: String,
    val uid: String
)
