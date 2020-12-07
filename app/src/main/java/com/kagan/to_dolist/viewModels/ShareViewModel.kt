package com.kagan.to_dolist.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kagan.to_dolist.models.Task

class ShareViewModel : ViewModel() {

    private val TAG ="SharedViewModel"
    val task = MutableLiveData<Task>()

    fun setTask(task: Task) {
        this.task.value = task
        Log.d(TAG, "setTask: $task")
        Log.d(TAG, "setTask: ${this.task.value}")
    }

    fun clear() = null.also { task.value = it }
}