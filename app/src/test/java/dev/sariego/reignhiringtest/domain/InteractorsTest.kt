package dev.sariego.reignhiringtest.domain

import androidx.test.filters.SmallTest
import dev.sariego.reignhiringtest.test.factory.ArticleFactory
import dev.sariego.reignhiringtest.domain.interactor.DeleteArticle
import dev.sariego.reignhiringtest.domain.interactor.ObserveArticles
import dev.sariego.reignhiringtest.domain.interactor.UpdateArticles
import dev.sariego.reignhiringtest.domain.repository.ArticlesRepository
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