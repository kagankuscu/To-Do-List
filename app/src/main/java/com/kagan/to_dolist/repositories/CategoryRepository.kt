package com.kagan.to_dolist.repositories

import androidx.lifecycle.MutableLiveData
import com.kagan.to_dolist.constants.Constant.MEETING
import com.kagan.to_dolist.constants.Constant.PERSONAL
import com.kagan.to_dolist.constants.Constant.SHOPPING
import com.kagan.to_dolist.constants.Constant.STUDY
import com.kagan.to_dolist.constants.Constant.WORK

class CategoryRepository {

    private val TAG = "FakeRepo"

    private val category = MutableLiveData<Map<String, Boolean>>()
    private val categoryMap = mutableMapOf<String, Boolean>()

    companion object {
        private var INSTANCE: CategoryRepository? = null

        fun getInstance(): CategoryRepository {
            var instance = INSTANCE

            if (instance == null) {
                instance = CategoryRepository()
            }
            return instance
        }
    }


    fun getCategory(): MutableLiveData<Map<String, Boolean>> {
        val personal = false
        val meeting = false
        val shopping = false
        val study = false
        val work = false

        categoryMap[PERSONAL] = personal
        categoryMap[MEETING] = meeting
        categoryMap[SHOPPING] = shopping
        categoryMap[STUDY] = study
        categoryMap[WORK] = work

        category.value = categoryMap


        return category
    }

    fun saveCategory() {
        categoryMap
    }
}