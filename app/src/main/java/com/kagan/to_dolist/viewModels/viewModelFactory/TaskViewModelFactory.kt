package com.kagan.to_dolist.viewModels.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kagan.to_dolist.repositories.CategoryRepository
import com.kagan.to_dolist.repositories.TaskRepository
import com.kagan.to_dolist.viewModels.CategoryViewModel
import com.kagan.to_dolist.viewModels.TaskViewModel

@Suppress("UNCHECKED_CAST")
class TaskViewModelFactory(private val repository: TaskRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}