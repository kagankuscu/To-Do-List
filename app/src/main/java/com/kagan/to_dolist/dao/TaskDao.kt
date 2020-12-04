package com.kagan.to_dolist.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.kagan.to_dolist.enums.Category
import com.kagan.to_dolist.models.Task
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.withContext

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(task: Task)

    @Delete
    suspend fun deleted(task: Task)

    @Query(value = "SELECT * FROM task")
    fun getAllTasks(): LiveData<ArrayList<Task>>
}