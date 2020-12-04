package dev.sariego.reignhiringtest.domain.interactor

import dev.sariego.reignhiringtest.domain.repository.ArticlesRepository

class UpdateArticles(private val repository: ArticlesRepository) {
    operator fun invoke() = repository.fetch()
}