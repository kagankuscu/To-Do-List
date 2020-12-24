package com.kagan.to_dolist.repositories

import android.util.Log
import com.kagan.to_dolist.db.CategoryDB
import com.kagan.to_dolist.enums.CategoryType
import com.kagan.to_dolist.models.Category

class CategoryRepository(private val db: CategoryDB) {

    private val TAG = "CategoryRepository"

    suspend fun save(category: Category) = db.getCategoryDao().upsert(category)

    fun getCategory() = db.getCategoryDao().getCategories()

    suspend fun delete(categoryType: CategoryType) = db.getCategoryDao().delete(categoryType)
}