package com.kagan.to_dolist.db

import com.kagan.to_dolist.dao.TaskDao

class TaskDB {
    fun getTaskDao(): TaskDao = TaskDao()
}