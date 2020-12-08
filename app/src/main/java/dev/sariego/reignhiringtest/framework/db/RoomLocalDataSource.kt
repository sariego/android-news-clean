package dev.sariego.reignhiringtest.framework.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import dev.sariego.reignhiringtest.data.local.ArticlesLocalDataSource
import dev.sariego.reignhiringtest.domain.entity.Article
import javax.inject.Inject

class RoomLocalDataSource @Inject constructor(
    private val db: AppDatabase,
    private val mapper: LocalDataArticleMapper,
) : ArticlesLocalDataSource {

    private val dao by lazy { db.articleDao() }

    override fun live(): LiveData<List<Article>> = with(mapper) {
        dao.getNonDeletedArticles().map { list -> list.map { data -> data.asArticle() } }
    }

    override fun delete(article: Article) = with(mapper) {
        dao.updateArticle(article.asData(deleted = true))
    }

    override fun addNew(vararg articles: Article) = with(mapper) {
        dao.insertOnlyNewArticles(*articles.map { article -> article.asData() }.toTypedArray())
    }
}
