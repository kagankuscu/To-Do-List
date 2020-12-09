package com.kagan.to_dolist.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kagan.to_dolist.enums.CategoryType
import com.kagan.to_dolist.models.Category

@Dao
interface CategoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(category: Category)

    @Query(value = "SELECT * FROM category_table")
    fun getCategories(): LiveData<Category>

    // Task Total
}