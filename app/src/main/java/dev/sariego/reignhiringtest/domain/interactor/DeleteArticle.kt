package dev.sariego.reignhiringtest.domain.interactor

import dev.sariego.reignhiringtest.domain.entity.Article
import dev.sariego.reignhiringtest.domain.repository.ArticlesRepository

class DeleteArticle(private val repository: ArticlesRepository) {
    operator fun invoke(article: Article) = repository.delete(article)
}