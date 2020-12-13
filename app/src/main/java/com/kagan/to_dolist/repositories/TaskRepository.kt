package com.kagan.to_dolist.repositories

import androidx.lifecycle.LiveData
import com.kagan.to_dolist.db.TaskDB
import com.kagan.to_dolist.enums.CategoryType
import com.kagan.to_dolist.models.Task

class TaskRepository(private val db: TaskDB) {

    private val TAG = "TaskRepo"

    fun getAllTask(): LiveData<List<Task>> {
        return db.getTaskDao().getAllTasks()
    }

    fun getAllTaskByCategory(categoryType: CategoryType): LiveData<List<Task>> {
        return db.getTaskDao().getAllTaskByCategory(categoryType)
    }

    suspend fun getTotalTaskByCategory(categoryType: CategoryType) =
        db.getTaskDao().getTotalTaskByCategory(categoryType)

    suspend fun save(task: Task) {
        db.getTaskDao().upsert(task)
    }
}