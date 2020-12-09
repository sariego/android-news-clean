package dev.sariego.reignhiringtest.test.factory

import dev.sariego.reignhiringtest.domain.entity.Article
import io.github.serpro69.kfaker.Faker
import java.net.URL
import java.util.*

object ArticleFactory {

    private val faker = Faker()

    fun make(): Article {
        val uuid = UUID.randomUUID()
        val url = URL("https", faker.internet.domain(), uuid.toString())
        return Article(
            id = uuid.hashCode(),
            title = faker.movie.title(),
            author = faker.artist.names(),
            created = Date(),
            url = url.toString(),
        )
    }

    fun makeList(
        capacity: Int = 10,
        transform: ArticleTransform? = null
    ): List<Article> =
        List(capacity) { position -> make().let { transform?.invoke(position, it) ?: it } }
}

