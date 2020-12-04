package dev.sariego.reignhiringtest.framework.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dev.sariego.reignhiringtest.data.ArticlesRepositoryImpl
import dev.sariego.reignhiringtest.data.local.ArticlesLocalDataSource
import dev.sariego.reignhiringtest.data.remote.ArticlesRemoteDataSource
import dev.sariego.reignhiringtest.domain.repository.ArticlesRepository
import dev.sariego.reignhiringtest.framework.db.RoomLocalDataSource
import dev.sariego.reignhiringtest.framework.net.HackerNewsRemoteDataSource

@Module
@InstallIn(ApplicationComponent::class)
interface DataModule {

    @Binds
    fun bindArticlesRepository(impl: ArticlesRepositoryImpl): ArticlesRepository

    @Binds
    fun bindLocalDataSource(impl: RoomLocalDataSource): ArticlesLocalDataSource

    @Binds
    fun bindRemoteDataSource(impl: HackerNewsRemoteDataSource): ArticlesRemoteDataSource
}