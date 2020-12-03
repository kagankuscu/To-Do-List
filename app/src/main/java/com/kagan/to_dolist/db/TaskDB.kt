package com.kagan.to_dolist.db

import com.kagan.to_dolist.dao.TaskDao

class TaskDB {
    var taskDao = TaskDao()
        private set

    companion object {
        @Volatile
        private var instance: TaskDB? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: TaskDB().also { instance = it }
            }
    }
}