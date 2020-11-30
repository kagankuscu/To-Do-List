package com.kagan.to_dolist.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kagan.to_dolist.enums.Category
import com.kagan.to_dolist.models.Task

class TaskRepository {

    private val TAG = "TaskViewModel"
    private var allTask = MutableLiveData<ArrayList<Task>>()
    private var taskByCategory = MutableLiveData<ArrayList<Task>>()

    companion object {
        private var INSTANCE: TaskRepository? = null

        fun getInstance(): TaskRepository {
            var instance = INSTANCE
            if (instance == null) {
                instance = TaskRepository()
            }

            return instance
        }
    }

    fun getAllTask(): LiveData<ArrayList<Task>> {
        val taskCategoryList = arrayListOf<Task>()

        taskCategoryList.add(
            Task(
                1,
                "Fragment empty",
                Category.PERSONAL,
                System.currentTimeMillis(),
                false
            )
        )

        taskCategoryList.add(
            Task(
                2,
                "Some work to do",
                Category.WORK,
                System.currentTimeMillis(),
                false
            )
        )

        taskCategoryList.add(
            Task(
                3,
                "Tomorrow will add database",
                Category.PERSONAL,
                System.currentTimeMillis(),
                false
            )
        )

        taskCategoryList.add(
            Task(
                4,
                "Study PDIR",
                Category.STUDY,
                System.currentTimeMillis(),
                false
            )
        )

        taskCategoryList.add(
            Task(
                5,
                "Watch Movie",
                Category.PERSONAL,
                System.currentTimeMillis(),
                false
            )
        )

        taskCategoryList.add(
            Task(
                6,
                "take a photography",
                Category.PERSONAL,
                System.currentTimeMillis(),
                false
            )
        )

        taskCategoryList.add(
            Task(
                7,
                "Buy lamp",
                Category.SHOPPING,
                System.currentTimeMillis(),
                false
            )
        )

        taskCategoryList.add(
            Task(
                8,
                "ride a bike",
                Category.PERSONAL,
                System.currentTimeMillis(),
                false
            )
        )

        allTask.value = taskCategoryList

        return allTask
    }

    fun getAllTaskByCategory(category: Category): LiveData<ArrayList<Task>> {
        val taskList = arrayListOf<Task>()

        when (category) {
            Category.PERSONAL -> {
                taskList.add(
                    Task(
                        1,
                        "Fragment empty",
                        Category.PERSONAL,
                        System.currentTimeMillis(),
                        false
                    )
                )

                taskList.add(
                    Task(
                        3,
                        "Tomorrow will add database",
                        Category.PERSONAL,
                        System.currentTimeMillis(),
                        false
                    )
                )

                taskList.add(
                    Task(
                        5,
                        "Watch Movie",
                        Category.PERSONAL,
                        System.currentTimeMillis(),
                        false
                    )
                )

                taskList.add(
                    Task(
                        6,
                        "take a photography",
                        Category.PERSONAL,
                        System.currentTimeMillis(),
                        false
                    )
                )

                taskList.add(
                    Task(
                        8,
                        "ride a bike",
                        Category.PERSONAL,
                        System.currentTimeMillis(),
                        false
                    )
                )
            }
            Category.MEETING -> {

            }

            Category.SHOPPING -> {
                taskList.add(
                    Task(
                        7,
                        "Buy lamp",
                        Category.SHOPPING,
                        System.currentTimeMillis(),
                        false
                    )
                )
            }

            Category.STUDY -> {
                taskList.add(
                    Task(
                        4,
                        "Study PDIR",
                        Category.STUDY,
                        System.currentTimeMillis(),
                        false
                    )
                )
            }

            Category.WORK -> {
                taskList.add(
                    Task(
                        2,
                        "Some work to do",
                        Category.WORK,
                        System.currentTimeMillis(),
                        false
                    )
                )

            }
        }

        taskByCategory.value = taskList

        return taskByCategory
    }

    suspend fun save() {

    }
}