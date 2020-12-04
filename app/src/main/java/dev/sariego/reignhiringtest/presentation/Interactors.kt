package dev.sariego.reignhiringtest.presentation

import dev.sariego.reignhiringtest.domain.interactor.DeleteArticle
import dev.sariego.reignhiringtest.domain.interactor.ObserveArticles
import dev.sariego.reignhiringtest.domain.interactor.UpdateArticles

data class Interactors(
    val observeArticles: ObserveArticles,
    val updateArticles: UpdateArticles,
    val deleteArticle: DeleteArticle,
)
