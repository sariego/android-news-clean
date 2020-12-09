package dev.sariego.reignhiringtest.framework.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dev.sariego.reignhiringtest.factory.ArticleFactory
import dev.sariego.reignhiringtest.factory.ArticleTransform
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.Comparator

@ExperimentalCoroutinesApi
@SmallTest
@RunWith(AndroidJUnit4::class)
class ArticleDaoTest {

    lateinit var db: AppDatabase
    lateinit var dao: ArticleDao

    private val mapper = LocalDataArticleMapper()
    private fun ArticleFactory.makeData() = this.make().let { with(mapper) { it.asData() } }
    private fun ArticleFactory.makeDataList(
        capacity: Int = 10,
        transform: ArticleTransform? = null,
    ) = this.makeList(capacity, transform).map { with(mapper) { it.asData() } }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.articleDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun observe_shouldOnlyShowNonDeletedItems() = runBlockingTest {
        val all = ArticleFactory.makeDataList()
            .mapIndexed { i, article ->
                article.takeIf { i == 0 }
                    ?.copy(deleted = true)
                    ?: article
            }
        val expected = all.first()
        val stream = dao.observeNonDeletedArticles()

        assertThat(stream.first()).containsNoneIn(all)
        dao.insertOnlyNewArticles(*all.toTypedArray())
        val actual = stream.first()
        assertThat(actual).doesNotContain(expected)
        assertThat(actual).containsAtLeastElementsIn(all.minus(expected))
    }

    @Test
    fun observe_shouldShowItemsInOrderNewestFirst() = runBlockingTest {
        val now = Date().toInstant()
        val expected = ArticleFactory.makeDataList { position, article ->
            article.copy(
                created = now.plus(position.toLong(), ChronoUnit.DAYS)
                    .toEpochMilli().let { Date(it) }
            )
        }
        dao.insertOnlyNewArticles(*expected.toTypedArray())

        val actual = dao.observeNonDeletedArticles().first()
        assertThat(actual).isInOrder(Comparator<LocalDataArticle> { a1, a2 ->
            // newest first
            -1 * a1.created.compareTo(a2.created)
        })
    }

    @Test
    fun insert_shouldAddNewItems() = runBlockingTest {
        val expected = ArticleFactory.makeDataList()
        val stream = dao.observeNonDeletedArticles()

        assertThat(stream.first()).containsNoneIn(expected)
        dao.insertOnlyNewArticles(*expected.toTypedArray())
        assertThat(stream.first()).containsAtLeastElementsIn(expected)
    }

    @Test
    fun insert_shouldNotAddExistingItems() = runBlockingTest {
        val existing = ArticleFactory.makeData()
        val new = ArticleFactory.makeData().copy(id = existing.id)
        val stream = dao.observeNonDeletedArticles()
        dao.insertOnlyNewArticles(existing)

        assertThat(stream.first()).contains(existing)
        dao.insertOnlyNewArticles(new)
        assertThat(stream.first()).contains(existing)
        assertThat(stream.first()).doesNotContain(new)
    }

    @Test
    fun update_shouldModifyExistingItem() = runBlockingTest {
        val existing = ArticleFactory.makeData()
        val new = ArticleFactory.makeData().copy(id = existing.id)
        val stream = dao.observeNonDeletedArticles()
        dao.insertOnlyNewArticles(existing)

        assertThat(stream.first()).contains(existing)
        dao.updateArticle(new)
        assertThat(stream.first()).contains(new)
        assertThat(stream.first()).doesNotContain(existing)
    }

    @Test
    fun update_shouldNotAddNewItem() = runBlockingTest {
        val expected = ArticleFactory.makeData()
        val stream = dao.observeNonDeletedArticles()

        assertThat(stream.first()).doesNotContain(expected)
        dao.updateArticle(expected)
        assertThat(stream.first()).doesNotContain(expected)
    }
}
