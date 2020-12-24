package com.kagan.to_dolist.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kagan.to_dolist.enums.CategoryType
import com.kagan.to_dolist.models.Category

@Dao
interface CategoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(category: Category)

    @Query(value = "SELECT * FROM category_table ORDER BY categoryType")
    fun getCategories(): LiveData<List<Category>>

    @Update
    suspend fun update(category: Category)

    @Query(value = "SELECT COUNT(id) FROM category_table")
    fun getCount(): Int

    @Query(value = "DELETE FROM category_table WHERE categoryType=:categoryType")
    suspend fun delete(categoryType: CategoryType)
}