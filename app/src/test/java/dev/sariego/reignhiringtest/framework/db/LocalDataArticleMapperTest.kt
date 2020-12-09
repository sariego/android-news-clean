package dev.sariego.reignhiringtest.framework.db

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dev.sariego.reignhiringtest.factory.ArticleFactory
import org.junit.Test
import kotlin.random.Random

@SmallTest
class LocalDataArticleMapperTest {

    private val mapper = LocalDataArticleMapper()

    @Test
    fun asArticle_shouldMapCorrectly() {
        val expected = with(mapper) { ArticleFactory.make().asData() }
        val actual = with(mapper) { expected.asArticle() }

        assertThat(actual.id).isEqualTo(expected.id)
        assertThat(actual.title).isEqualTo(expected.title)
        assertThat(actual.author).isEqualTo(expected.author)
        assertThat(actual.created).isEqualTo(expected.created)
        assertThat(actual.url).isEqualTo(expected.url)
    }

    @Test
    fun asData_shouldMapCorrectly() {
        val expected = ArticleFactory.make()
        val deleted = Random.nextBoolean()
        val actual = with(mapper) { expected.asData(deleted) }

        assertThat(actual.id).isEqualTo(expected.id)
        assertThat(actual.title).isEqualTo(expected.title)
        assertThat(actual.author).isEqualTo(expected.author)
        assertThat(actual.created).isEqualTo(expected.created)
        assertThat(actual.url).isEqualTo(expected.url)
        assertThat(actual.deleted).isEqualTo(deleted)
    }
}