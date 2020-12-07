package com.kagan.to_dolist.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kagan.to_dolist.R
import com.kagan.to_dolist.adapters.TaskAdapter
import com.kagan.to_dolist.constants.Constant.MEETING
import com.kagan.to_dolist.constants.Constant.PERSONAL
import com.kagan.to_dolist.constants.Constant.SHOPPING
import com.kagan.to_dolist.constants.Constant.STUDY
import com.kagan.to_dolist.constants.Constant.WORK
import com.kagan.to_dolist.constants.SimpleDateFormat
import com.kagan.to_dolist.databinding.FragmentTaskBinding
import com.kagan.to_dolist.db.TaskDB
import com.kagan.to_dolist.enums.Category
import com.kagan.to_dolist.models.Task
import com.kagan.to_dolist.repositories.TaskRepository
import com.kagan.to_dolist.viewModels.ShareViewModel
import com.kagan.to_dolist.viewModels.TaskViewModel
import com.kagan.to_dolist.viewModels.viewModelFactory.TaskViewModelFactory

class TaskFragment : Fragment(R.layout.fragment_task) {

    val TAG = "TaskFragment"
    private lateinit var binding: FragmentTaskBinding
    private val safeargs: TaskFragmentArgs by navArgs()
    private lateinit var db: TaskDB
    private lateinit var repository: TaskRepository
    private lateinit var shareViewModel: ShareViewModel
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskViewModelFactory: TaskViewModelFactory
    private lateinit var adapter: TaskAdapter
    private val mTasks = ArrayList<Task>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTaskBinding.bind(view)

        binding.tvName.text = safeargs.category
        binding.taskRecyclerView.recyclerView.adapter = adapter
        binding.btnAdd.setOnClickListener {
            navigateToAddTask()
        }
    }

    private fun observeTasksByCategory(): Unit {
        taskViewModel.getTasksByCategory(getCategoryName()).observe(this, {
            mTasks.clear()
            mTasks.addAll(it)
            if (it.isNotEmpty()) {
                adapter.notifyItemInserted(mTasks.size - 1)
                binding.taskRecyclerView.hideEmptyView()
            } else {
                binding.taskRecyclerView.showEmptyView()
            }
        })
    }

    private fun observeSharedViewModel() {
        shareViewModel.task.observe(this, {
            it?.let {
                taskViewModel.saveTask(it)
                shareViewModel.clear()
            }
        })
    }

    private fun navigateToAddTask() {
        findNavController().navigate(R.id.action_taskFragment_to_newTaskFragment)
    }

    private fun getCategoryName(): Category {
        return when (safeargs.category) {
            PERSONAL -> Category.PERSONAL
            MEETING -> Category.MEETING
            SHOPPING -> Category.SHOPPING
            STUDY -> Category.STUDY
            WORK -> Category.WORK
            else -> Category.EMPTY
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = TaskDB(requireContext())
        repository = TaskRepository(db)
        taskViewModelFactory = TaskViewModelFactory(repository)
        taskViewModel = ViewModelProvider(this, taskViewModelFactory).get(TaskViewModel::class.java)
        shareViewModel = ViewModelProvider(requireActivity()).get(ShareViewModel::class.java)

        taskViewModel.getTasksByCategory(getCategoryName())
        adapter = TaskAdapter(mTasks)

        observeSharedViewModel()
        observeTasksByCategory()
    }
}