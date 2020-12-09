package dev.sariego.reignhiringtest.factory

import dev.sariego.reignhiringtest.domain.entity.Article

typealias ArticleTransform = (position: Int, article: Article) -> Article
