package dev.sariego.reignhiringtest

import android.app.Application
import android.content.Context
import androidx.room.Room
import dev.sariego.reignhiringtest.data.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "pls-hire-me"
        )
            .build()
    }

    companion object {
        lateinit var context: Context
            private set

        lateinit var db: AppDatabase
            private set
    }
}