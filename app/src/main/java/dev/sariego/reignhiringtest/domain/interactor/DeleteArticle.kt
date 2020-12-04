package dev.sariego.reignhiringtest.domain.interactor

import dev.sariego.reignhiringtest.domain.entity.Article
import dev.sariego.reignhiringtest.domain.repository.ArticlesRepository
import javax.inject.Inject

class DeleteArticle @Inject constructor(private val repository: ArticlesRepository) {
    operator fun invoke(article: Article) = repository.delete(article)
}