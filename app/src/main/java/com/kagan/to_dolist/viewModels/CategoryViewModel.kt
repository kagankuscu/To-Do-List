package com.kagan.to_dolist.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.kagan.to_dolist.constants.Constant.PERSONAL
import com.kagan.to_dolist.repositories.CategoryRepository

class CategoryViewModel : ViewModel() {

    private val TAG = "ViewModel"
    private var repository: CategoryRepository = CategoryRepository.getInstance()
    private var categories = MutableLiveData<Map<String, Boolean>>()
    var isEmpty: Boolean = true

    init {
        Log.d(TAG, "init: ViewModel")
        Log.d(TAG, "repo: $repository")
        categories = repository.getCategory()
        Log.d(TAG, "categories: ${categories.value} ")
        setEmpty()
    }


    fun getCategory(): LiveData<Map<String, Boolean>> {
        return categories
    }

    fun saved(categoriesMap: Map<String, Boolean>) {
        categories.value = categoriesMap

        repository.saveCategory()
    }

    private fun setEmpty() {
        categories.value?.forEach { _, value ->
            if (value) {
                isEmpty = false
                return@forEach
            }
        }
    }
}