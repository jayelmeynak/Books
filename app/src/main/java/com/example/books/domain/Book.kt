package com.example.books.domain

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val key: String = "",
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val category: String = "",
    val imageUrl: String = ""
)