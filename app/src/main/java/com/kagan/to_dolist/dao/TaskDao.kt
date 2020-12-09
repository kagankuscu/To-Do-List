package com.kagan.to_dolist.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kagan.to_dolist.enums.CategoryType
import com.kagan.to_dolist.models.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(task: Task)

    @Delete
    suspend fun deleted(task: Task)

    @Query(value = "SELECT * FROM task_table")
    fun getAllTasks(): LiveData<List<Task>>

    @Query(value = "SELECT * FROM task_table WHERE categoryType LIKE :categoryType")
    fun getAllTaskByCategory(categoryType: CategoryType): LiveData<List<Task>>

    @Query(value = "SELECT COUNT(id) from task_table WHERE categoryType LIKE :categoryType")
    fun getTotalTaskByCategory(categoryType: CategoryType): LiveData<Int>
}