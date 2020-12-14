package dev.sariego.reignhiringtest.test.fake

import dev.sariego.reignhiringtest.domain.entity.Article
import dev.sariego.reignhiringtest.domain.repository.ArticlesRepository
import dev.sariego.reignhiringtest.test.factory.ArticleFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryArticlesRepository(
    initialItems: List<Article> = emptyList()
) : ArticlesRepository {

    private val items = initialItems.toMutableList()
    private val flow = MutableStateFlow(initialItems)

    val currentItems: List<Article> get() = items

    override fun stream(): Flow<List<Article>> = flow.asStateFlow()

    override suspend fun fetch() {
        items.addAll(ArticleFactory.makeList())
        flow.value = items
    }

    override suspend fun delete(article: Article) {
        items.remove(article)
        flow.value = items
    }
}