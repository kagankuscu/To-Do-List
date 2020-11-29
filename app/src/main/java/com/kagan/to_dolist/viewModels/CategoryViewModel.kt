package com.kagan.to_dolist.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kagan.to_dolist.repositories.CategoryRepository

class CategoryViewModel : ViewModel() {

    private val TAG = "ViewModel"
    private var repository: CategoryRepository = CategoryRepository.getInstance()
    private var categories = MutableLiveData<Map<String, Boolean>>()

    init {
        Log.d(TAG, "init: ViewModel")
        Log.d(TAG, "repo: $repository")
        categories = repository.getCategory()
        Log.d(TAG, "categories: ${categories.value} ")
    }


    fun getCategory(): LiveData<Map<String, Boolean>> {
        return categories
    }

    fun saved(categoriesMap: Map<String, Boolean>) {
        categories.value = categoriesMap

        repository.saveCategory()
    }
}