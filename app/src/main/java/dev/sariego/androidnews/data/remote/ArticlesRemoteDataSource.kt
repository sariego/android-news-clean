package dev.sariego.androidnews.data.remote

import dev.sariego.androidnews.domain.entity.Article

interface ArticlesRemoteDataSource {

    suspend fun get(): List<Article>
}