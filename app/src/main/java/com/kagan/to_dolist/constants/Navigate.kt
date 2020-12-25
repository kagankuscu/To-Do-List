package com.kagan.to_dolist.constants

import com.kagan.to_dolist.enums.CategoryType
import com.kagan.to_dolist.ui.CategoryFragmentDirections
import java.util.*

interface Navigate {
    fun navigate(categoryType: CategoryType, categoryName: String)
}