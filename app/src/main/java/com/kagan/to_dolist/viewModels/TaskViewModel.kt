package com.kagan.to_dolist.viewModels

import android.util.Log
import androidx.core.os.persistableBundleOf
import androidx.lifecycle.*
import com.kagan.to_dolist.enums.CategoryType
import com.kagan.to_dolist.models.Task
import com.kagan.to_dolist.repositories.TaskRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private val TAG = "TaskViewModel"

    private var currentFiltering = "ALL_TASK"

    private val _category = MutableLiveData<CategoryType>()
    private val _items: LiveData<List<Task>> = _category.switchMap { category ->
        repository.getAllTaskByCategory(category).switchMap {
            filterTask(it)
        }
    }

    val items: LiveData<List<Task>> = _items

    fun getAllTasks(): LiveData<List<Task>> {
        return repository.getAllTask()
    }

    fun getTasksByCategory(categoryType: CategoryType): LiveData<List<Task>> {
        return repository.getAllTaskByCategory(categoryType)
    }

    fun getCompletedTasks(categoryType: CategoryType) = repository.getCompletedTasks(categoryType)

    fun getUnCompletedTasks(categoryType: CategoryType) =
        repository.getUnCompletedTasks(categoryType)

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

    fun restore(task: Task) = viewModelScope.launch(IO) {
        repository.restore(task)
    }

    suspend fun getTaskById(itemId: Long) = repository.getTaskById(itemId)

    fun updateTask(addUpdateTask: Task) = viewModelScope.launch {
        Log.d("NewTask ", "updateTask: $addUpdateTask")
        repository.updateTask(addUpdateTask)
    }

    fun loadTasks(categoryType: CategoryType) {
        _category.value = categoryType
    }

    private fun filterTask(tasks: List<Task>): LiveData<List<Task>> {
        val result = MutableLiveData<List<Task>>()

        viewModelScope.launch {
            result.value = filterItems(tasks, currentFiltering)
        }

        return result
    }

    private fun filterItems(tasks: List<Task>, filteringType: String): List<Task> {
        val tasksToShow = ArrayList<Task>()

        for (task in tasks) {
            when (filteringType) {
                "ALL_TASK" -> tasksToShow.add(task)
                "COMPLETED_TASK" -> if (task.isCompleted) {
                    tasksToShow.add(task)
                }
                "UNCOMPLETED_TASK" -> if (!task.isCompleted) {
                    tasksToShow.add(task)
                }
            }
        }
        return tasksToShow
    }

    fun setFiltering(requestType: String, categoryType: CategoryType) {
        currentFiltering = requestType

        loadTasks(categoryType)
    }

    fun deleteAllTaskByCategory(categoryType: CategoryType) = viewModelScope.launch {
        repository.deleteAllTaskByCategory(categoryType)
    }
}