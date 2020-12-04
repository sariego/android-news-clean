package dev.sariego.reignhiringtest.framework.db

import dev.sariego.reignhiringtest.domain.entity.Article

class LocalDataArticleMapper {

    fun LocalDataArticle.asArticle() = Article(
        id = this.id,
        title = this.title,
        author = this.author,
        created = this.created,
        url = this.url,
    )

    fun Article.asData() = LocalDataArticle(
        id = this.id,
        title = this.title,
        author = this.author,
        created = this.created,
        url = this.url,
    )
}