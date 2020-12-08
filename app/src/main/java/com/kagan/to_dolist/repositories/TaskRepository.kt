package com.kagan.to_dolist.repositories

import androidx.lifecycle.LiveData
import com.kagan.to_dolist.db.TaskDB
import com.kagan.to_dolist.enums.Category
import com.kagan.to_dolist.models.Task

class TaskRepository(private val db: TaskDB) {

    private val TAG = "TaskRepo"

    fun getAllTask(): LiveData<List<Task>> {
        return db.getTaskDao().getAllTasks()
    }

    fun getAllTaskByCategory(category: Category): LiveData<List<Task>> {
        return db.getTaskDao().getAllTaskByCategory(category)
    }

    fun getTotalTaskByCategory(category: Category) =
        db.getTaskDao().getTotalTaskByCategory(category)

    suspend fun save(task: Task) {
        db.getTaskDao().upsert(task)
    }
}