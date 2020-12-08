package dev.sariego.reignhiringtest

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
            title = faker.movie.unique.title(),
            author = faker.artist.unique.names(),
            created = Date(),
            url = url.toString(),
        )
    }

    fun makeList(
        capacity: Int = 10,
        transform: (position: Int, article: Article) -> Article = { _, a -> a }
    ): List<Article> =
        List(capacity) { transform(it, make()) }
}