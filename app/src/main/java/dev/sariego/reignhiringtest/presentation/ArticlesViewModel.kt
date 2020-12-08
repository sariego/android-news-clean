package dev.sariego.reignhiringtest.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.sariego.reignhiringtest.domain.entity.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticlesViewModel @ViewModelInject constructor(
    private val interactors: Interactors,
) : ViewModel() {

    init {
        update()
    }

    fun visibleArticles(): LiveData<List<Article>> = interactors.observeArticles()

    fun delete(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        interactors.deleteArticle(article)
    }

    fun update() = viewModelScope.launch(Dispatchers.IO) { interactors.updateArticles() }
}