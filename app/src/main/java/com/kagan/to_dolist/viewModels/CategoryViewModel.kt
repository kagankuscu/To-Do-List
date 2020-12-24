package com.kagan.to_dolist.viewModels

import androidx.lifecycle.*
import com.kagan.to_dolist.enums.CategoryType
import com.kagan.to_dolist.models.Category
import com.kagan.to_dolist.repositories.CategoryRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: CategoryRepository) : ViewModel() {

    private val TAG = "ViewModel"

    fun save(category: Category) =
        viewModelScope.launch(IO) {
            repository.save(category)
        }

    fun getCategory() = repository.getCategory()

    fun delete(categoryType: CategoryType) = viewModelScope.launch {
        repository.delete(categoryType)
    }

}