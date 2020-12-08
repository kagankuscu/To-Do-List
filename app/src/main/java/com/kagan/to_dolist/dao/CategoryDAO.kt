package com.kagan.to_dolist.dao

import androidx.room.Dao
import androidx.room.Query
import com.kagan.to_dolist.enums.Category

@Dao
interface CategoryDAO {


    // Task Total
    @Query(value = "SELECT COUNT(id) from task_table WHERE category LIKE :category")
    fun getTotalTaskByCategory(category: Category)
}