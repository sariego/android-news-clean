package dev.sariego.reignhiringtest.data.remote

import dev.sariego.reignhiringtest.domain.entity.Article

interface ArticlesRemoteSource {

    fun get(): List<Article>
}