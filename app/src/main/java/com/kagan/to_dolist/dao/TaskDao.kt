package com.kagan.to_dolist.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kagan.to_dolist.enums.Category
import com.kagan.to_dolist.models.Task
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.withContext

class TaskDao {

    private val tasksByCategoryList = arrayListOf<Task>()
    private val tasksByCategory = MutableLiveData<ArrayList<Task>>()

    fun getAllTask(): LiveData<ArrayList<Task>> {
        return tasksByCategory
    }

    fun getAllTaskByCategory(category: Category): LiveData<ArrayList<Task>> {
        return tasksByCategory
    }

    suspend fun save(task: Task) {
        tasksByCategoryList.add(task)
        withContext(Main) {
            tasksByCategory.value = tasksByCategoryList
        }
    }
}