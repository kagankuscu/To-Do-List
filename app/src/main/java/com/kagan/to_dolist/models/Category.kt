package com.kagan.to_dolist.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey(autoGenerate = false)
    val id: Int= 1,
    var personal: Boolean,
    var meeting: Boolean,
    var shopping: Boolean,
    var study: Boolean,
    var work: Boolean
)
