package com.kagan.to_dolist.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kagan.to_dolist.enums.CategoryType
import com.kagan.to_dolist.models.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(task: Task)

    @Update
    suspend fun deleted(task: Task)

    @Query(value = "SELECT * FROM task_table")
    fun getAllTasks(): LiveData<List<Task>>

    @Query(value = "SELECT * FROM task_table WHERE categoryType=:categoryType AND isDeleted=0")
    fun getAllTaskByCategory(categoryType: CategoryType): LiveData<List<Task>>

    @Query(value = "SELECT COUNT(id) from task_table WHERE categoryType=:categoryType AND isDeleted=0")
    suspend fun getTotalTaskByCategory(categoryType: CategoryType): Int

    @Update
    suspend fun completed(task: Task)

    @Update
    suspend fun restore(task: Task)

    @Query(value = "SELECT * FROM task_table WHERE id=:itemId")
    suspend fun getTaskById(itemId: Long): Task

    @Update
    suspend fun updateTask(addUpdateTask: Task)
}