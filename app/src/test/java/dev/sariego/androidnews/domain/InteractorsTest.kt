package dev.sariego.androidnews.domain

import androidx.test.filters.SmallTest
import dev.sariego.androidnews.domain.interactor.DeleteArticle
import dev.sariego.androidnews.domain.interactor.ObserveArticles
import dev.sariego.androidnews.domain.interactor.UpdateArticles
import dev.sariego.androidnews.domain.repository.ArticlesRepository
import dev.sariego.androidnews.test.factory.ArticleFactory
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@SmallTest
@ExperimentalCoroutinesApi
class InteractorsTest {

    @MockK
    lateinit var repository: ArticlesRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
    }

    @Test
    fun observeInvocation_usesRepositoryStream() {
        val observeInteractor = ObserveArticles(repository)

        observeInteractor()
        verify { repository.stream() }
    }

    @Test
    fun updateInvocation_usesRepositoryFetch() = runBlockingTest {
        val updateInteractor = UpdateArticles(repository)

        updateInteractor()
        coVerify { repository.fetch() }
    }

    @Test
    fun deleteInvocation_usesRepositoryDelete() = runBlockingTest {
        val expected = ArticleFactory.make()
        val deleteInteractor = DeleteArticle(repository)

        deleteInteractor(expected)
        coVerify { repository.delete(expected) }
    }

}