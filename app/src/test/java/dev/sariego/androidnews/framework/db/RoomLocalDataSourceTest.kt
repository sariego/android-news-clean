package dev.sariego.androidnews.framework.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import dev.sariego.androidnews.test.factory.ArticleFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@MediumTest
@RunWith(AndroidJUnit4::class)
class RoomLocalDataSourceTest {

    lateinit var source: RoomLocalDataSource
    lateinit var db: AppDatabase

    private val mapper = LocalDataArticleMapper()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        source = RoomLocalDataSource(db, mapper)
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun addNew_addsItemsToStream() = runBlockingTest {
        val expected = ArticleFactory.makeList()
        val stream = source.stream()

        assertThat(stream.first()).containsNoneIn(expected)
        source.addNew(*expected.toTypedArray())
        assertThat(stream.first()).containsAtLeastElementsIn(expected)
    }

    @Test
    fun delete_removesItemsFromStream() = runBlockingTest {
        val all = ArticleFactory.makeList()
        val expected = all.first()

        source.addNew(*all.toTypedArray())
        val stream = source.stream()

        assertThat(stream.first()).containsAtLeastElementsIn(all)
        source.delete(expected)
        val actual = stream.first()
        assertThat(actual).doesNotContain(expected)
        assertThat(actual).containsAtLeastElementsIn(all.minus(expected))
    }

}