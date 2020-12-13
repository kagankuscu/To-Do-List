package com.kagan.to_dolist.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kagan.to_dolist.enums.CategoryType
import com.kagan.to_dolist.models.Task
import com.kagan.to_dolist.repositories.TaskRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private val TAG = "TaskViewModel"

    fun getAllTasks(): LiveData<List<Task>> {
        return repository.getAllTask()
    }

    fun getTasksByCategory(categoryType: CategoryType): LiveData<List<Task>> {
        return repository.getAllTaskByCategory(categoryType)
    }

    suspend fun getTotalTaskByCategory(categoryType: CategoryType) =
        repository.getTotalTaskByCategory(categoryType)

    fun saveTask(task: Task) {
        viewModelScope.launch(IO) {
            repository.save(task)
        }
    }

    fun completed(task: Task) = viewModelScope.launch(IO) {
        repository.completed(task)
    }

    fun delete(task: Task) = viewModelScope.launch(IO) {
        repository.delete(task)
    }
}