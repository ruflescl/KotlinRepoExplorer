package com.pinkyra.kotlinrepoexplorer

import android.app.Application
import com.pinkyra.kotlinrepoexplorer.room.AppDatabase

open class CustomApplication : Application() {
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getInstance(this)
    }
}