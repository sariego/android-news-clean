package dev.sariego.androidnews.domain.entity

import java.util.*

data class Article(
    val id: Int,
    val title: String,
    val author: String,
    val created: Date,
    val url: String? = null,
)