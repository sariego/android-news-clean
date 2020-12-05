package dev.sariego.reignhiringtest.framework.di

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.room.Room
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.sariego.reignhiringtest.framework.db.AppDatabase
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
    fun provideVolleyRequestQueue(@ApplicationContext context: Context): RequestQueue = Volley
        .newRequestQueue(context)

    @Provides
    @Singleton
    fun provideBrowserIntent(): CustomTabsIntent = CustomTabsIntent.Builder().build()

}