package dev.sariego.reignhiringtest.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sariego.reignhiringtest.domain.entity.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ArticlesViewModel @ViewModelInject constructor(
    private val interactors: Interactors,
) : ViewModel() {

    fun visibleArticles(): StateFlow<List<Article>> =
        interactors.observeArticles().stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    fun delete(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        interactors.deleteArticle(article)
    }

    fun update() = viewModelScope.launch(Dispatchers.IO) {
        interactors.updateArticles()
    }
}