package com.kagan.to_dolist.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(
    val personal: Boolean,
    val meeting: Boolean,
    val shopping: Boolean,
    val study: Boolean,
    val work: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
