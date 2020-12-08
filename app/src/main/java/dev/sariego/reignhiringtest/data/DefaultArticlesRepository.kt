package dev.sariego.reignhiringtest.data

import dev.sariego.reignhiringtest.data.local.ArticlesLocalDataSource
import dev.sariego.reignhiringtest.data.remote.ArticlesRemoteDataSource
import dev.sariego.reignhiringtest.domain.entity.Article
import dev.sariego.reignhiringtest.domain.repository.ArticlesRepository
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