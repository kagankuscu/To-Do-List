package com.kagan.to_dolist.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kagan.to_dolist.models.Task

class ShareViewModel : ViewModel() {

    private val TAG = "SharedViewModel"
    val task = MutableLiveData<Task>()
    val taskToNew = MutableLiveData<Task>()

    fun setTask(task: Task) {
        this.task.value = task
        Log.d(TAG, "this.task.value: ${this.task.value}")
    }

    fun setTaskToNew(task: Task) {
        taskToNew.value = task
        Log.d(TAG, "taskToNewvalue: ${taskToNew.value}")
    }

    fun getUpdateTake() = taskToNew

    fun clear() = null.also { task.value = it }
}