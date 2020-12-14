package dev.sariego.androidnews.presentation

import dev.sariego.androidnews.domain.interactor.DeleteArticle
import dev.sariego.androidnews.domain.interactor.ObserveArticles
import dev.sariego.androidnews.domain.interactor.UpdateArticles
import javax.inject.Inject

data class Interactors @Inject constructor(
    val observeArticles: ObserveArticles,
    val updateArticles: UpdateArticles,
    val deleteArticle: DeleteArticle,
)
