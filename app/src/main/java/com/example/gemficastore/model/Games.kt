package com.example.gemficastore.model

data class Games(
    val id: Long,
    val imageUrl: String,
    val title: String,
    val description: String,
    val price: String,
    val genre: List<String>,
    val externalLink: String,
    var favorite: Boolean = false,
)