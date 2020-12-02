package com.kagan.to_dolist.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kagan.to_dolist.enums.Category
import com.kagan.to_dolist.models.Task

class TaskRepository {

    private val TAG = "TaskRepo"
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
                        System.currentTimeMillis() + 15_000,
                        false
                    )
                )

                taskList.add(
                    Task(
                        3,
                        "Tomorrow will add database",
                        Category.PERSONAL,
                        System.currentTimeMillis() + 34_000_000,
                        false
                    )
                )

                taskList.add(
                    Task(
                        5,
                        "Watch Movie",
                        Category.PERSONAL,
                        System.currentTimeMillis() + 1,
                        false
                    )
                )

                taskList.add(
                    Task(
                        6,
                        "take a photography",
                        Category.PERSONAL,
                        System.currentTimeMillis() + 5_000,
                        false
                    )
                )

                taskList.add(
                    Task(
                        8,
                        "ride a bike",
                        Category.PERSONAL,
                        System.currentTimeMillis() + 60_000,
                        false
                    )
                )
            }
            Category.MEETING -> {
                taskList.add(
                    Task(
                        10,
                        "Meet old friends ",
                        Category.MEETING,
                        System.currentTimeMillis() + 10_000,
                        false
                    )
                )

                taskList.add(
                    Task(
                        11,
                        "Important meeting",
                        Category.MEETING,
                        System.currentTimeMillis() + 2000,
                        false
                    )
                )

                taskList.add(
                    Task(
                        12,
                        "12:00 meeting",
                        Category.MEETING,
                        System.currentTimeMillis() + 10,
                        false
                    )
                )
            }

            Category.SHOPPING -> {
                taskList.add(
                    Task(
                        7,
                        "Buy lamp",
                        Category.SHOPPING,
                        System.currentTimeMillis() + 50_000,
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
                        System.currentTimeMillis() + 12_211,
                        false
                    )
                )
                taskList.add(
                    Task(
                        9,
                        "Study ERDSC",
                        Category.STUDY,
                        System.currentTimeMillis() + 11,
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
                        System.currentTimeMillis() + 10_210,
                        false
                    )
                )

            }
        }

        taskByCategory.value = taskList

        return taskByCategory
    }

    suspend fun save(task: Task) {

    }
}