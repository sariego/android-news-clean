package dev.sariego.androidnews.data

import dev.sariego.androidnews.data.local.ArticlesLocalDataSource
import dev.sariego.androidnews.data.remote.ArticlesRemoteDataSource
import dev.sariego.androidnews.domain.entity.Article
import dev.sariego.androidnews.domain.repository.ArticlesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultArticlesRepository @Inject constructor(
    private val local: ArticlesLocalDataSource,
    private val remote: ArticlesRemoteDataSource,
) : ArticlesRepository {

    override fun stream(): Flow<List<Article>> = local.stream()

    override suspend fun fetch() = local.addNew(*remote.get().toTypedArray())

    override suspend fun delete(article: Article) = local.delete(article)
}