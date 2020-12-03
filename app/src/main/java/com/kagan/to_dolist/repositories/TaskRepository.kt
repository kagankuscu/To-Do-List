package com.kagan.to_dolist.repositories

import androidx.lifecycle.LiveData
import com.kagan.to_dolist.db.TaskDB
import com.kagan.to_dolist.enums.Category
import com.kagan.to_dolist.models.Task

class TaskRepository(private val db: TaskDB) {

    private val TAG = "TaskRepo"

    fun getAllTask(): LiveData<ArrayList<Task>> {
        return db.taskDao.getAllTask()
    }

    fun getAllTaskByCategory(category: Category): LiveData<ArrayList<Task>> {
        return db.taskDao.getAllTaskByCategory(category)
    }

    suspend fun save(task: Task) {
        db.taskDao.save(task)
    }
}