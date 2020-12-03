package com.kagan.to_dolist.repositories

import androidx.lifecycle.MutableLiveData
import com.kagan.to_dolist.constants.Constant.MEETING
import com.kagan.to_dolist.constants.Constant.PERSONAL
import com.kagan.to_dolist.constants.Constant.SHOPPING
import com.kagan.to_dolist.constants.Constant.STUDY
import com.kagan.to_dolist.constants.Constant.WORK
import com.kagan.to_dolist.dao.CategoryDAO
import com.kagan.to_dolist.db.CategoryDB

class CategoryRepository(private val db: CategoryDB, private val categoryDao: CategoryDAO) {

    private val TAG = "FakeRepo"

    private val category = MutableLiveData<Map<String, Boolean>>()
    private val categoryMap = mutableMapOf<String, Boolean>()

    fun getCategory(): MutableLiveData<Map<String, Boolean>> {
        val personal = true
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