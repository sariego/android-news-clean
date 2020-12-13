package dev.sariego.reignhiringtest.framework.net

import dev.sariego.reignhiringtest.domain.entity.Article
import java.util.*
import javax.inject.Inject

class RemoteDataArticleMapper @Inject constructor() {

    fun RemoteDataArticle.asArticle() = Article(
        id = this.id ?: 0,
        title = this.storyTitle ?: this.title.orEmpty(),
        author = this.author.orEmpty(),
        url = this.storyUrl ?: this.url,
        created = this.created ?: Date(0),
    )
}