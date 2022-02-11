package com.jerryokafor.compose.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jerryokafor.compose.data.db.dao.UserDao
import com.jerryokafor.compose.data.db.entity.UserEntity

/**
 * @Author <Author>
 * @Project <Project>
 */
@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private const val DB_NAME = "compose-template.db"

        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context, useInMemory: Boolean = false): AppDatabase {
            val dbBuilder = if (useInMemory) Room.inMemoryDatabaseBuilder(
                context,
                AppDatabase::class.java
            ).allowMainThreadQueries()
            else Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()

            return INSTANCE ?: dbBuilder.build().also { INSTANCE = it }
        }
    }
}