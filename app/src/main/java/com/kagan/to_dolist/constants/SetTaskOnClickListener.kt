package com.kagan.to_dolist.constants

import com.kagan.to_dolist.models.Task

interface SetTaskOnClickListener {
    fun onItemClick(position: Int, task: Task)
}