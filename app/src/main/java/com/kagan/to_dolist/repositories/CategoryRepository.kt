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

    private val TAG = "CategoryRepository"

    suspend fun save(category: Category) {
        val count = db.getCategoryDao().getCount()
        Log.d(TAG, "save: count=${count}")
        if (count == 0) {
            db.getCategoryDao().upsert(category)
        } else {
            db.getCategoryDao().update(category)
        }
    }

    fun getCategory() = db.getCategoryDao().getCategories()
}