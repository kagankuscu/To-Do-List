package com.kagan.to_dolist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kagan.to_dolist.dao.TaskDao
import com.kagan.to_dolist.db.converters.CategoryConverter
import com.kagan.to_dolist.models.Task

@Database(
    entities = [Task::class],
    version = 3
)
@TypeConverters(CategoryConverter::class)
abstract class TaskDB : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao

    companion object {
        @Volatile
        private var instance: TaskDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TaskDB::class.java, "TaskDB.db"
            ).fallbackToDestructiveMigration().build()
    }
}