package dev.sariego.reignhiringtest.framework.net

import dev.sariego.reignhiringtest.data.remote.ArticlesRemoteDataSource
import dev.sariego.reignhiringtest.domain.entity.Article
import javax.inject.Inject

class HackerNewsRemoteDataSource @Inject constructor(
    private val service: HackerNewsService,
    private val mapper: RemoteDataArticleMapper,
) : ArticlesRemoteDataSource {

    override suspend fun get(): List<Article> {
        val response = service.search()
        return with(mapper) {
            response.hits.map { it.asArticle() }
        }
    }
}