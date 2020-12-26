package com.kagan.to_dolist.constants

import com.kagan.to_dolist.enums.CategoryType

interface Navigate {
    fun navigate(categoryType: CategoryType, categoryName: String)
}