package dev.sariego.reignhiringtest.data

import androidx.lifecycle.LiveData
import dev.sariego.reignhiringtest.data.local.ArticlesLocalDataSource
import dev.sariego.reignhiringtest.data.remote.ArticlesRemoteDataSource
import dev.sariego.reignhiringtest.domain.entity.Article
import dev.sariego.reignhiringtest.domain.repository.ArticlesRepository
import javax.inject.Inject

class DefaultArticlesRepository @Inject constructor(
    private val local: ArticlesLocalDataSource,
    private val remote: ArticlesRemoteDataSource,
) : ArticlesRepository {

    override fun live(): LiveData<List<Article>> = local.live()

    override fun fetch() = local.addNew(*remote.get().toTypedArray())

    override fun delete(article: Article) = local.delete(article)
}