package com.kagan.to_dolist.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kagan.to_dolist.enums.CategoryType

@Entity(tableName = "task_table")
data class Task(
    val title: String,
    val categoryType: CategoryType,
    val dueDateTime: Long = 0,
    var isCompleted: Boolean = false,
    val isDeleted: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}