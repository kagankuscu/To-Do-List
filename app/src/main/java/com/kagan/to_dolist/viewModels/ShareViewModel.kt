package com.kagan.to_dolist.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kagan.to_dolist.enums.CategoryType
import com.kagan.to_dolist.models.Task

class ShareViewModel : ViewModel() {

    private val TAG = "SharedViewModel"
    private var categoryType: CategoryType = CategoryType.EMPTY
    private var categoryDel: Boolean = false
    val task = MutableLiveData<Task>()

    fun setTask(task: Task) {
        this.task.value = task
        Log.d(TAG, "this.task.value: ${this.task.value}")
    }

    fun clear() = null.also { task.value = it }

    fun setCategoryDelete(category: CategoryType, isDelete: Boolean) {
        categoryType = category
        categoryDel = isDelete
    }

    fun getCategoryDelete() = mutableMapOf(categoryType to categoryDel)

    fun clearCategoryDelete() {
        categoryType = CategoryType.EMPTY
        categoryDel = false
    }
}