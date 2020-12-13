package com.kagan.to_dolist.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kagan.to_dolist.enums.CategoryType

data class CategoryTaskCount(
    var categoryType: CategoryType,
    var taskCount: String
)
