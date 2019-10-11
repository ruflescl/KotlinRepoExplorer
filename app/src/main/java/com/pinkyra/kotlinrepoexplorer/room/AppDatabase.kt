package com.pinkyra.kotlinrepoexplorer.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.local.dao.RepositoryDetailDao
import com.pinkyra.kotlinrepoexplorer.feature.explorer.repository.local.dto.RepositoryDetailDto

@Database(entities = [RepositoryDetailDto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repositoryDetailDao(): RepositoryDetailDao

    companion object {
        var TEST_MODE = false
        private const val databaseName = "github_repository"

        private var db: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (db == null) {
                db = if (TEST_MODE) {
                    Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                        .allowMainThreadQueries()
                        .build()
                } else {
                    Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
                        .build()
                }
            }
            return db!!
        }

        private fun close() {
            db?.close()
        }
    }
}