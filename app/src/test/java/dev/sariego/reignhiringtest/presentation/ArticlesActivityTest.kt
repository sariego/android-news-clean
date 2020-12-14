package dev.sariego.reignhiringtest.presentation

import android.content.Intent
import androidx.lifecycle.Lifecycle.State
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dev.sariego.reignhiringtest.R
import dev.sariego.reignhiringtest.domain.repository.ArticlesRepository
import dev.sariego.reignhiringtest.framework.di.DataModule
import dev.sariego.reignhiringtest.test.fake.InMemoryArticlesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import dev.sariego.reignhiringtest.presentation.adapter.ArticlesAdapter.ViewHolder as VH


@MediumTest
@HiltAndroidTest
@UninstallModules(DataModule::class)
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class ArticlesActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(ArticlesActivity::class.java)

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    val repository: ArticlesRepository = InMemoryArticlesRepository()
    private val items get() = (repository as InMemoryArticlesRepository).currentItems

    @Test
    fun activity_initializes() {
        val scenario = activityRule.scenario
        scenario.moveToState(State.RESUMED)
        onView(withId(R.id.recycler))
            .check(matches(isDisplayed()))
        scenario.onActivity {
            assertThat(it.adapter).isNotNull()
            assertThat(it.browserIntent).isNotNull()
        }
    }

    @Test
    fun activity_showsItems() {
        val scenario = activityRule.scenario
        scenario.moveToState(State.RESUMED)

        onView(withId(R.id.recycler))
            .perform(scrollToPosition<VH>(9))

//        assertThat(items.size).isEqualTo(10)
        onView(withText(items[9].title)).check(matches(isDisplayed()))
    }

    @Test
    fun clickOnItem_launchesViewIntent() {
        val scenario = activityRule.scenario
        scenario.moveToState(State.RESUMED)

        Intents.init()
        onView(withId(R.id.recycler))
            .perform(scrollToPosition<VH>(9))
            .perform(actionOnItemAtPosition<VH>(9, click()))

        val expected = items[9].url
        intended(hasAction(Intent.ACTION_VIEW))
        intended(hasData(expected))
        Intents.release()
    }

    @Test
    fun swipeOnItem_removesItemFromList() {
        val scenario = activityRule.scenario
        scenario.moveToState(State.RESUMED)

//        assertThat(items.size).isEqualTo(10)
        val expected = items[9]
        onView(withId(R.id.recycler))
            .perform(scrollToPosition<VH>(9))
            .perform(actionOnItemAtPosition<VH>(9, swipeRight()))

        assertThat(items).doesNotContain(expected)
    }

    @Test
    @Ignore("Robolectric doesn't support swipe refresh layout")
    fun swipeDown_performsRefresh() {
        val scenario = activityRule.scenario
        scenario.moveToState(State.RESUMED)

        assertThat(items.size).isEqualTo(10)
        onView(withId(R.id.layoutSwipeRefresh))
            .perform(swipeDown())
        onView(withId(R.id.recycler))
            .perform(scrollToPosition<VH>(20))

        assertThat(items.size).isGreaterThan(10)
    }
}