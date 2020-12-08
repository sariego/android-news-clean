package dev.sariego.reignhiringtest.framework.db

import dev.sariego.reignhiringtest.domain.entity.Article
import javax.inject.Inject

class LocalDataArticleMapper @Inject constructor() {

    fun LocalDataArticle.asArticle() = Article(
        id = this.id,
        title = this.title,
        author = this.author,
        created = this.created,
        url = this.url,
    )

    fun Article.asData(deleted: Boolean = false) = LocalDataArticle(
        id = this.id,
        title = this.title,
        author = this.author,
        created = this.created,
        url = this.url,
        deleted = deleted,
    )
}