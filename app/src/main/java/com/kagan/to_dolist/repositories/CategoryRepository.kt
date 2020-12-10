package com.kagan.to_dolist.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kagan.to_dolist.constants.Constant.MEETING
import com.kagan.to_dolist.constants.Constant.PERSONAL
import com.kagan.to_dolist.constants.Constant.SHOPPING
import com.kagan.to_dolist.constants.Constant.STUDY
import com.kagan.to_dolist.constants.Constant.WORK
import com.kagan.to_dolist.dao.CategoryDAO
import com.kagan.to_dolist.db.CategoryDB
import com.kagan.to_dolist.models.Category

class CategoryRepository(private val db: CategoryDB) {

    private val TAG = "FakeRepo"

    private val category = MutableLiveData<Map<String, Boolean>>()
    private val categoryMap = mutableMapOf<String, Boolean>()

    suspend fun save(category: Category) {
        val count = db.getCategoryDao().getCount()
        Log.d(TAG, "save: count=${count}")
        if (count == 0) {
            db.getCategoryDao().upsert(category)
        } else {
            db.getCategoryDao().update(category)
        }
    }

    fun getCategoryDB() = db.getCategoryDao().getCategories()

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