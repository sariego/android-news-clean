package dev.sariego.reignhiringtest.domain.repository

import androidx.lifecycle.LiveData
import dev.sariego.reignhiringtest.domain.entity.Article

interface ArticlesRepository {

    // return stream of non-deleted available articles
    fun live(): LiveData<List<Article>>

    // get new articles
    fun fetch()

    // delete article
    fun delete(article: Article)
}
