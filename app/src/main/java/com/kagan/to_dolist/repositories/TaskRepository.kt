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

    fun getCompletedTasks(categoryType: CategoryType) =
        db.getTaskDao().getCompletedTasks(categoryType)

    fun getUnCompletedTasks(categoryType: CategoryType) =
        db.getTaskDao().getUnCompletedTasks(categoryType)

    suspend fun getTotalTaskByCategory(categoryType: CategoryType) =
        db.getTaskDao().getTotalTaskByCategory(categoryType)

    suspend fun save(task: Task) {
        db.getTaskDao().upsert(task)
    }

    suspend fun completed(task: Task) = db.getTaskDao().completed(task)

    suspend fun delete(task: Task) = db.getTaskDao().deleted(task)

    suspend fun restore(task: Task) = db.getTaskDao().restore(task)

    suspend fun getTaskById(itemId: Long) = db.getTaskDao().getTaskById(itemId)

    suspend fun updateTask(addUpdateTask: Task) = db.getTaskDao().updateTask(addUpdateTask)

    suspend fun deleteAllTaskByCategory(categoryType: CategoryType)= db.getTaskDao().deleteAllTaskByCategory(categoryType)

    fun todayTask()= db.getTaskDao().todayTasks()
}