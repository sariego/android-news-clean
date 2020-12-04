package dev.sariego.reignhiringtest.data.local

import androidx.lifecycle.LiveData
import dev.sariego.reignhiringtest.domain.entity.Article

interface ArticlesLocalSource {

    fun live(): LiveData<List<Article>>

    fun delete(article: Article)

    fun addNew(vararg articles: Article)
}