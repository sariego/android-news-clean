package dev.sariego.androidnews.domain.interactor

import dev.sariego.androidnews.domain.repository.ArticlesRepository
import javax.inject.Inject

class ObserveArticles @Inject constructor(private val repository: ArticlesRepository) {
    operator fun invoke() = repository.stream()
}