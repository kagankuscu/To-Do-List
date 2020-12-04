package com.kagan.to_dolist.enums.converters

import androidx.room.TypeConverter
import com.kagan.to_dolist.enums.Category

class CategoryConverter {

    @TypeConverter
    fun fromCategory(value: Category): Int = value.ordinal

    @TypeConverter
    fun toCategory(value: Int): Category = when (value) {
        0 -> Category.PERSONAL
        1 -> Category.MEETING
        2 -> Category.SHOPPING
        3 -> Category.STUDY
        4 -> Category.WORK
        else -> Category.EMPTY
    }
}