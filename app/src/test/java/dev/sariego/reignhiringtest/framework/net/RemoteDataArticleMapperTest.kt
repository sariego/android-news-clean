package dev.sariego.reignhiringtest.framework.net

import com.google.common.truth.Truth.assertThat
import dev.sariego.reignhiringtest.test.factory.DataArticleFactory
import org.junit.Test

class RemoteDataArticleMapperTest {


    private val mapper = RemoteDataArticleMapper()

    @Test
    fun asArticle_mapsCorrectly() {
        val expected = DataArticleFactory.makeRemote()
        val actual = with(mapper) { expected.asArticle() }

        assertThat(actual.id).isEqualTo(expected.id)
        assertThat(actual.title).isEqualTo(expected.storyTitle)
        assertThat(actual.author).isEqualTo(expected.author)
        assertThat(actual.created).isEqualTo(expected.created)
        assertThat(actual.url).isEqualTo(expected.storyUrl)
    }

    @Test
    fun asArticle_usesFallbacks() {
        val expected = DataArticleFactory.makeRemote().copy(
            storyTitle = null,
            storyUrl = null,
        )
        val actual = with(mapper) { expected.asArticle() }

        assertThat(actual.title).isEqualTo(expected.title)
        assertThat(actual.url).isEqualTo(expected.url)
    }
}