package dev.sariego.reignhiringtest.domain.repository

import dev.sariego.reignhiringtest.domain.entity.Article
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {

    // return stream of non-deleted available articles
    fun stream(): Flow<List<Article>>

    // get new articles
    suspend fun fetch()

    // delete article
    suspend fun delete(article: Article)
}
