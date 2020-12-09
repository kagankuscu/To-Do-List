package com.kagan.to_dolist.db.converters

import androidx.room.TypeConverter
import com.kagan.to_dolist.enums.CategoryType

class CategoryConverter {

    @TypeConverter
    fun fromCategory(value: CategoryType): Int = value.ordinal

    @TypeConverter
    fun toCategory(value: Int): CategoryType = when (value) {
        0 -> CategoryType.PERSONAL
        1 -> CategoryType.MEETING
        2 -> CategoryType.SHOPPING
        3 -> CategoryType.STUDY
        4 -> CategoryType.WORK
        else -> CategoryType.EMPTY
    }
}