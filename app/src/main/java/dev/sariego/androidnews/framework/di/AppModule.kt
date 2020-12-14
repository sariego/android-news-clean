package dev.sariego.androidnews.framework.di

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.sariego.androidnews.framework.db.AppDatabase
import dev.sariego.androidnews.framework.di.qualifier.ServerUrl
import dev.sariego.androidnews.framework.net.HackerNewsService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "pls-hire-me"
    ).build()

    @Provides
    @Singleton
    fun provideBrowserIntent(): CustomTabsIntent = CustomTabsIntent.Builder().build()

    @Provides
    fun provideNetworkService(
        @ServerUrl serverUrl: String,
        moshi: Moshi,
    ): HackerNewsService {
        val retrofit = Retrofit.Builder()
            .baseUrl(serverUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        return retrofit.create(HackerNewsService::class.java)
    }

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()

}