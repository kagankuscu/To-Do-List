package com.kagan.to_dolist.models

import com.kagan.to_dolist.enums.Category

data class Task(
    val id: Long,
    val title: String,
    val category: Category,
    val dueDateTime: Long,
    val isDeleted: Boolean
)