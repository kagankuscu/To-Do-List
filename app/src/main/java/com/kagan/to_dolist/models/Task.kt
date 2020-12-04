package com.kagan.to_dolist.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kagan.to_dolist.enums.Category

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val category: Category,
    val dueDateTime: Long = 0,
    val isDeleted: Boolean = false
)