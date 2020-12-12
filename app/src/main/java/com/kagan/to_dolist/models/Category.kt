package com.kagan.to_dolist.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kagan.to_dolist.enums.CategoryType

@Entity(tableName = "category_table")
data class Category(
    val categoryType: CategoryType
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
