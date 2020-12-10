package com.kagan.to_dolist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kagan.to_dolist.dao.CategoryDAO
import com.kagan.to_dolist.models.Category

@Database(
    entities = [Category::class],
    version = 2
)
abstract class CategoryDB : RoomDatabase() {
    abstract fun getCategoryDao(): CategoryDAO

    companion object {
        @Volatile
        private var instance: CategoryDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CategoryDB::class.java,
            "CategoryDB.db"
        ).fallbackToDestructiveMigration().build()
    }

}