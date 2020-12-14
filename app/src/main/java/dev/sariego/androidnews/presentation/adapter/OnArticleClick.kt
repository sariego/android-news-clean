package dev.sariego.androidnews.presentation.adapter

import android.net.Uri

typealias OnArticleClick = (position: Int, uri: Uri?) -> Unit

fun ArticlesAdapter.onArticleClick(listener: OnArticleClick) {
    this.onArticleClick = listener
}