package dev.sariego.reignhiringtest.framework.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import dev.sariego.reignhiringtest.data.local.ArticlesLocalSource
import dev.sariego.reignhiringtest.domain.entity.Article
import dev.sariego.reignhiringtest.framework.App

class RoomLocalSource : ArticlesLocalSource {

    private val dao = App.db.articleDao()
    private val mapper = LocalDataArticleMapper()

    override fun live(): LiveData<List<Article>> = with(mapper) {
        dao.getNonDeletedArticles().map { list -> list.map { data -> data.asArticle() } }
    }

    override fun delete(article: Article) = with(mapper) {
        dao.updateArticle(article.asData().copy(deleted = true))
    }

    override fun addNew(vararg articles: Article) = with(mapper) {
        dao.insertOnlyNewArticles(*articles.map { article -> article.asData() }.toTypedArray())
    }


}