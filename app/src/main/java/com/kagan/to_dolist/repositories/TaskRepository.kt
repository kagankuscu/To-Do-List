package com.kagan.to_dolist.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kagan.to_dolist.dao.TaskDao
import com.kagan.to_dolist.db.TaskDB
import com.kagan.to_dolist.enums.Category
import com.kagan.to_dolist.models.Task

class TaskRepository(private val db: TaskDB, private val taskDao: TaskDao) {

    private val TAG = "TaskRepo"

    fun getAllTask(): LiveData<ArrayList<Task>> {
        return taskDao.getAllTask()
    }

    fun getAllTaskByCategory(category: Category): LiveData<ArrayList<Task>> {
        return taskDao.getAllTaskByCategory()
    }

    suspend fun save(task: Task) {
        taskDao.save(task)
    }
}