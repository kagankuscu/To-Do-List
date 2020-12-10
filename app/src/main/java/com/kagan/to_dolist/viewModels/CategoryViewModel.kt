package com.kagan.to_dolist.viewModels

import android.util.Log
import androidx.lifecycle.*
import com.kagan.to_dolist.models.Category
import com.kagan.to_dolist.repositories.CategoryRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: CategoryRepository) : ViewModel() {

    private val TAG = "ViewModel"
    var isEmpty: Boolean = true

    init {
//        setEmpty()
    }

    fun save(category: Category) =
        viewModelScope.launch(IO) {
            repository.save(category)
        }

    fun getCategory() = repository.getCategory()

    private fun setEmpty() {
//        categories.value?.forEach { _, value ->
//            if (value) {
//                isEmpty = false
//                return@forEach
//            }
//        }
    }
}