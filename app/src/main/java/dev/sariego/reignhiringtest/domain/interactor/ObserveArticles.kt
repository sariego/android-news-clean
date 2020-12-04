package dev.sariego.reignhiringtest.domain.interactor

import dev.sariego.reignhiringtest.domain.repository.ArticlesRepository
import javax.inject.Inject

class ObserveArticles @Inject constructor(private val repository: ArticlesRepository) {
    operator fun invoke() = repository.live()
}