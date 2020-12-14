package dev.sariego.androidnews.domain.interactor

import dev.sariego.androidnews.domain.entity.Article
import dev.sariego.androidnews.domain.repository.ArticlesRepository
import javax.inject.Inject

class DeleteArticle @Inject constructor(private val repository: ArticlesRepository) {
    suspend operator fun invoke(article: Article) = repository.delete(article)
}