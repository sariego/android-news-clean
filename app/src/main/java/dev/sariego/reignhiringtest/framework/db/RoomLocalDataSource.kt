package dev.sariego.reignhiringtest.framework.db

import dev.sariego.reignhiringtest.data.local.ArticlesLocalDataSource
import dev.sariego.reignhiringtest.domain.entity.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomLocalDataSource @Inject constructor(
    private val db: AppDatabase,
    private val mapper: LocalDataArticleMapper,
) : ArticlesLocalDataSource {

    private val dao by lazy { db.articleDao() }

    override fun stream(): Flow<List<Article>> = with(mapper) {
        dao.observeNonDeletedArticles().map { list -> list.map { data -> data.asArticle() } }
    }

    override suspend fun delete(article: Article) = with(mapper) {
        dao.updateArticle(article.asData(deleted = true))
    }

    override suspend fun addNew(vararg articles: Article) = with(mapper) {
        dao.insertOnlyNewArticles(*articles.map { article -> article.asData() }.toTypedArray())
    }
}
