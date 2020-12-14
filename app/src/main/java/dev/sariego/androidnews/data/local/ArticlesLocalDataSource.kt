package dev.sariego.androidnews.data.local

import dev.sariego.androidnews.domain.entity.Article
import kotlinx.coroutines.flow.Flow

interface ArticlesLocalDataSource {

    fun stream(): Flow<List<Article>>

    suspend fun delete(article: Article)

    suspend fun addNew(vararg articles: Article)
}