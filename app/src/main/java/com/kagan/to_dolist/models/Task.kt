package com.kagan.to_dolist.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kagan.to_dolist.enums.CategoryType

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    val title: String,
    val categoryType: CategoryType,
    val dueDate: String = "",
    val dueTime: String = "",
    var isCompleted: Boolean = false,
    var isDeleted: Boolean = false
)