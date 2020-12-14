package dev.sariego.androidnews.framework.net

import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dev.sariego.androidnews.framework.di.NetModule
import dev.sariego.androidnews.framework.di.qualifier.ServerUrl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@UninstallModules(NetModule::class)
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class HackerNewsRemoteDataSourceTest {

    lateinit var server: MockWebServer
    lateinit var serverUrl: String

    @Module
    @InstallIn(ApplicationComponent::class)
    inner class TestModule {

        @Provides
        @ServerUrl
        fun provideMockServerUrl() = serverUrl
    }

    @Inject
    lateinit var source: HackerNewsRemoteDataSource

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        server = MockWebServer().apply {
            enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody("{}")
            )
            start()
        }
        serverUrl = server.url("/").toString()
        hiltRule.inject()
    }

    @After
    fun teardown() {
        server.shutdown()
    }

    @Test
    fun get_usesCorrectEndpoint() = runBlocking {
        source.get()
        val actual = Uri.parse(server.takeRequest().path)
        assertThat(actual.path)
            .isEqualTo("/${HackerNewsApi.SEARCH_PATH}")
        assertThat(actual.getQueryParameter(HackerNewsApi.SEARCH_QUERY_PARAM_KEY))
            .isEqualTo(HackerNewsApi.SEARCH_QUERY_PARAM_VALUE)
    }
}