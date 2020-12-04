package dev.sariego.reignhiringtest.presentation

import dev.sariego.reignhiringtest.domain.interactor.DeleteArticle
import dev.sariego.reignhiringtest.domain.interactor.ObserveArticles
import dev.sariego.reignhiringtest.domain.interactor.UpdateArticles
import javax.inject.Inject

data class Interactors @Inject constructor(
    val observeArticles: ObserveArticles,
    val updateArticles: UpdateArticles,
    val deleteArticle: DeleteArticle,
)
