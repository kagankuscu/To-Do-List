package com.kagan.to_dolist.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kagan.to_dolist.enums.Category
import com.kagan.to_dolist.models.Task

class TaskDao {

    private val tasksByCategory = MutableLiveData<ArrayList<Task>>()

    fun getAllTask(): LiveData<ArrayList<Task>> {
        return tasksByCategory
    }

    fun getAllTaskByCategory(category: Category): LiveData<ArrayList<Task>> {
        return tasksByCategory
    }

    fun save(task: Task) {
        tasksByCategory.value?.add(task)
    }
}