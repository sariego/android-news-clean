package dev.sariego.reignhiringtest.test.factory

import dev.sariego.reignhiringtest.framework.db.LocalDataArticle
import dev.sariego.reignhiringtest.framework.net.RemoteDataArticle
import io.github.serpro69.kfaker.Faker
import java.net.URL
import java.util.*

object DataArticleFactory {

    private val faker = Faker()

    fun makeLocal(): LocalDataArticle {
        val uuid = UUID.randomUUID()
        val url = URL("https", faker.internet.domain(), "/$uuid")
        return LocalDataArticle(
            id = uuid.hashCode(),
            title = faker.movie.title(),
            author = faker.artist.names(),
            created = Date(),
            url = url.toString(),
            deleted = false,
        )
    }

    fun makeLocalList(capacity: Int = 10): List<LocalDataArticle> =
        List(capacity) { makeLocal() }

    fun makeRemote(): RemoteDataArticle {
        val uuid = UUID.randomUUID()
        val url = URL("https", faker.internet.domain(), "/$uuid")
        return RemoteDataArticle(
            id = uuid.hashCode(),
            title = faker.movie.title(),
            storyTitle = faker.movie.title(),
            author = faker.artist.names(),
            created = Date(),
            url = url.toString(),
            storyUrl = url.toString(),
        )
    }
}