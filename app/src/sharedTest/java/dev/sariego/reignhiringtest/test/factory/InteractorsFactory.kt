package dev.sariego.reignhiringtest.test.factory

import dev.sariego.reignhiringtest.domain.interactor.DeleteArticle
import dev.sariego.reignhiringtest.domain.interactor.ObserveArticles
import dev.sariego.reignhiringtest.domain.interactor.UpdateArticles
import dev.sariego.reignhiringtest.domain.repository.ArticlesRepository
import dev.sariego.reignhiringtest.presentation.Interactors

object InteractorsFactory {

    fun make(repository: ArticlesRepository) = Interactors(
        ObserveArticles(repository),
        UpdateArticles(repository),
        DeleteArticle(repository),
    )
}