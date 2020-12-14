package dev.sariego.androidnews.test.factory

import dev.sariego.androidnews.domain.interactor.DeleteArticle
import dev.sariego.androidnews.domain.interactor.ObserveArticles
import dev.sariego.androidnews.domain.interactor.UpdateArticles
import dev.sariego.androidnews.domain.repository.ArticlesRepository
import dev.sariego.androidnews.presentation.Interactors

object InteractorsFactory {

    fun make(repository: ArticlesRepository) = Interactors(
        ObserveArticles(repository),
        UpdateArticles(repository),
        DeleteArticle(repository),
    )
}