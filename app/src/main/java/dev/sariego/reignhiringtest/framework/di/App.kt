package dev.sariego.reignhiringtest.framework.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.hilt.android.HiltAndroidApp
import dev.sariego.reignhiringtest.framework.db.AppDatabase

@HiltAndroidApp
class App : Application()