package com.kagan.to_dolist.db

import com.kagan.to_dolist.dao.CategoryDAO

class CategoryDB {
    fun getCategoryDao(): CategoryDAO {
        return CategoryDAO()
    }

    companion object {
        @Volatile
        private var INSTANCE: CategoryDB? = null

        fun getInstance(): CategoryDB {
            var instance = INSTANCE
            if (instance == null) {
                instance = CategoryDB()
            }
            return instance
        }
    }

}