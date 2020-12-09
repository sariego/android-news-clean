package dev.sariego.reignhiringtest.test.fake

import dev.sariego.reignhiringtest.domain.entity.Article
import dev.sariego.reignhiringtest.domain.repository.ArticlesRepository
import dev.sariego.reignhiringtest.test.factory.ArticleFactory
import kotlinx.coroutines.flow.*

class InMemoryArticlesRepository : ArticlesRepository {

    private val items = mutableListOf<Article>()
    private val state = MutableStateFlow(items.toList())

    override fun stream(): Flow<List<Article>>  = state

    override suspend fun fetch() {
        items.addAll(ArticleFactory.makeList())
        state.value = items
    }

    override suspend fun delete(article: Article) {
        items.remove(article)
        state.value = items
    }
}