package dev.sariego.reignhiringtest.domain.interactor

import dev.sariego.reignhiringtest.domain.repository.ArticlesRepository
import javax.inject.Inject

class UpdateArticles @Inject constructor(private val repository: ArticlesRepository) {
    suspend operator fun invoke() = repository.fetch()
}