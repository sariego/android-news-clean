package dev.sariego.reignhiringtest.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sariego.reignhiringtest.data.ArticlesRepositoryImpl
import dev.sariego.reignhiringtest.domain.entity.Article
import dev.sariego.reignhiringtest.domain.interactor.DeleteArticle
import dev.sariego.reignhiringtest.domain.interactor.ObserveArticles
import dev.sariego.reignhiringtest.domain.interactor.UpdateArticles
import dev.sariego.reignhiringtest.framework.db.RoomLocalSource
import dev.sariego.reignhiringtest.framework.net.HackerNewsRemoteSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticlesViewModel : ViewModel() {

    private val interactors = {
        val repo = ArticlesRepositoryImpl(
            RoomLocalSource(),
            HackerNewsRemoteSource(),
        )
        Interactors(
            ObserveArticles(repo),
            UpdateArticles(repo),
            DeleteArticle(repo),
        )
    }()

    fun updateAndObserve(): LiveData<List<Article>> {
        update()
        return interactors.observeArticles()
    }

    fun delete(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        interactors.deleteArticle(article)
    }

    fun update() = viewModelScope.launch(Dispatchers.IO) { interactors.updateArticles() }
}