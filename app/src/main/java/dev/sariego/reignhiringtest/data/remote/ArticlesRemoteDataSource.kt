package dev.sariego.reignhiringtest.data.remote

import dev.sariego.reignhiringtest.domain.entity.Article

interface ArticlesRemoteDataSource {

    fun get(): List<Article>
}