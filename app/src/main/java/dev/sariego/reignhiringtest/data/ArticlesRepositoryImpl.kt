package dev.sariego.reignhiringtest.data

import androidx.lifecycle.LiveData
import dev.sariego.reignhiringtest.data.local.ArticlesLocalSource
import dev.sariego.reignhiringtest.data.remote.ArticlesRemoteSource
import dev.sariego.reignhiringtest.domain.entity.Article
import dev.sariego.reignhiringtest.domain.repository.ArticlesRepository

class ArticlesRepositoryImpl(
    private val local: ArticlesLocalSource,
    private val remote: ArticlesRemoteSource,
) : ArticlesRepository {

    override fun live(): LiveData<List<Article>> = local.live()

    override fun fetch() = local.addNew(*remote.get().toTypedArray())

    override fun delete(article: Article) = local.delete(article)
}