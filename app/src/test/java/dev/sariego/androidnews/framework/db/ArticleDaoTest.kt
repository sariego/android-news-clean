package dev.sariego.androidnews.framework.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dev.sariego.androidnews.test.factory.DataArticleFactory
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
    fun observe_showsOnlyNonDeletedItems() = runBlockingTest {
        val all = DataArticleFactory.makeLocalList()
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
    fun observe_showsItemsInOrderNewestFirst() = runBlockingTest {
        val now = Date().toInstant()
        val expected = DataArticleFactory.makeLocalList()
            .mapIndexed { i, article ->
                article.copy(
                    created = now.plus(i.toLong(), ChronoUnit.DAYS)
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
    fun insert_addsNewItems() = runBlockingTest {
        val expected = DataArticleFactory.makeLocalList()
        val stream = dao.observeNonDeletedArticles()

        assertThat(stream.first()).containsNoneIn(expected)
        dao.insertOnlyNewArticles(*expected.toTypedArray())
        assertThat(stream.first()).containsAtLeastElementsIn(expected)
    }

    @Test
    fun insert_doNotAddsExistingItems() = runBlockingTest {
        val existing = DataArticleFactory.makeLocal()
        val new = DataArticleFactory.makeLocal().copy(id = existing.id)
        val stream = dao.observeNonDeletedArticles()
        dao.insertOnlyNewArticles(existing)

        assertThat(stream.first()).contains(existing)
        dao.insertOnlyNewArticles(new)
        assertThat(stream.first()).contains(existing)
        assertThat(stream.first()).doesNotContain(new)
    }

    @Test
    fun update_modifiesExistingItem() = runBlockingTest {
        val existing = DataArticleFactory.makeLocal()
        val new = DataArticleFactory.makeLocal().copy(id = existing.id)
        val stream = dao.observeNonDeletedArticles()
        dao.insertOnlyNewArticles(existing)

        assertThat(stream.first()).contains(existing)
        dao.updateArticle(new)
        assertThat(stream.first()).contains(new)
        assertThat(stream.first()).doesNotContain(existing)
    }

    @Test
    fun update_doNotAddsNewItem() = runBlockingTest {
        val expected = DataArticleFactory.makeLocal()
        val stream = dao.observeNonDeletedArticles()

        assertThat(stream.first()).doesNotContain(expected)
        dao.updateArticle(expected)
        assertThat(stream.first()).doesNotContain(expected)
    }
}
