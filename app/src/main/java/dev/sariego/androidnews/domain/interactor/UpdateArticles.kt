package dev.sariego.androidnews.domain.interactor

import dev.sariego.androidnews.domain.repository.ArticlesRepository
import javax.inject.Inject

class UpdateArticles @Inject constructor(private val repository: ArticlesRepository) {
    suspend operator fun invoke() = repository.fetch()
}