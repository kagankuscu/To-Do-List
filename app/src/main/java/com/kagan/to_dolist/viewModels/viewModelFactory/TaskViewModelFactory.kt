package com.kagan.to_dolist.viewModels.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kagan.to_dolist.repositories.CategoryRepository
import com.kagan.to_dolist.viewModels.CategoryViewModel

@Suppress("UNCHECKED_CAST")
class CategoryViewModelFactory(private val repository: CategoryRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}