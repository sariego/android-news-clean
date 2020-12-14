package dev.sariego.reignhiringtest.presentation

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dev.sariego.reignhiringtest.test.factory.ArticleFactory
import dev.sariego.reignhiringtest.test.factory.InteractorsFactory
import dev.sariego.reignhiringtest.test.fake.InMemoryArticlesRepository
import dev.sariego.reignhiringtest.test.rule.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

@SmallTest
@ExperimentalCoroutinesApi
class ArticlesViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun delete_removesItemFromState(): Unit = runBlocking {
        val all = ArticleFactory.makeList()
        val expected = all.first()
        val repo = InMemoryArticlesRepository(initialItems = all)
        val model = ArticlesViewModel(InteractorsFactory.make(repo))

        val stream = model.visibleArticles()
        assertThat(stream.first()).containsExactlyElementsIn(all)

        model.delete(expected).join()
        assertThat(stream.first()).containsExactlyElementsIn(all.minus(expected))
    }

    @Test
    fun update_addsNewItemsToState(): Unit = runBlocking {
        val repo = InMemoryArticlesRepository()
        val model = ArticlesViewModel(InteractorsFactory.make(repo))

        val stream = model.visibleArticles()
        assertThat(stream.first()).isEmpty()

        model.update().join()
        assertThat(stream.first()).isNotEmpty()
    }
}