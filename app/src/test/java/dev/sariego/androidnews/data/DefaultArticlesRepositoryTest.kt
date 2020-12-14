package dev.sariego.androidnews.data

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dev.sariego.androidnews.data.local.ArticlesLocalDataSource
import dev.sariego.androidnews.data.remote.ArticlesRemoteDataSource
import dev.sariego.androidnews.test.factory.ArticleFactory
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@SmallTest
@ExperimentalCoroutinesApi
class DefaultArticlesRepositoryTest {

    lateinit var repository: DefaultArticlesRepository

    @MockK
    lateinit var local: ArticlesLocalDataSource

    @MockK
    lateinit var remote: ArticlesRemoteDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = DefaultArticlesRepository(local, remote)
    }

    @Test
    fun stream_hasSameContentThanLocal() = runBlockingTest {
        val expected = ArticleFactory.makeList()
        every { local.stream() } returns flowOf(expected)

        val actual = repository.stream().first()
        assertThat(actual).containsAtLeastElementsIn(expected)
    }

    @Test
    fun fetch_savesRemoteContentIntoLocal() = runBlockingTest {
        val expected = ArticleFactory.makeList()
        coEvery { remote.get() } returns expected

        repository.fetch()
        coVerify { local.addNew(*expected.toTypedArray()) }
    }

    @Test
    fun delete_deletesFromLocal() = runBlockingTest {
        val expected = ArticleFactory.make()

        repository.delete(expected)
        coVerify { local.delete(expected) }
    }
}
