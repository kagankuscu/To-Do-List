package com.kagan.to_dolist.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kagan.to_dolist.enums.Category
import com.kagan.to_dolist.models.Task
import com.kagan.to_dolist.repositories.TaskRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private val TAG = "TaskViewModel"

    fun getAllTasks(): LiveData<List<Task>> {
        return repository.getAllTask()
    }

    fun getTasksByCategory(category: Category): LiveData<List<Task>> {
        return repository.getAllTaskByCategory(category)
    }

    fun getTotalTaskByCategory(category: Category) = repository.getTotalTaskByCategory(category)

    fun saveTask(task: Task) {
        viewModelScope.launch(IO) {
            repository.save(task)
        }
    }
}