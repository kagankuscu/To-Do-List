package com.kagan.to_dolist.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kagan.to_dolist.R
import com.kagan.to_dolist.adapters.TaskAdapter
import com.kagan.to_dolist.databinding.FragmentTaskBinding
import com.kagan.to_dolist.db.TaskDB
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

        binding.tvName.text = safeargs.category.name
        binding.taskRecyclerView.recyclerView.adapter = adapter
        binding.btnAdd.setOnClickListener {
            navigateToAddTask()
        }
    }

    private fun observeTasksByCategory(): Unit {
        taskViewModel.getTasksByCategory(safeargs.category).observe(this, {
            mTasks.clear()
            mTasks.addAll(it)
            binding.taskRecyclerView.recyclerView.scrollToPosition(mTasks.size - 1)
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
        val action = TaskFragmentDirections.actionTaskFragmentToNewTaskFragment(safeargs.category)
        findNavController().navigate(action)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = TaskDB(requireContext())
        repository = TaskRepository(db)
        taskViewModelFactory = TaskViewModelFactory(repository)
        taskViewModel = ViewModelProvider(this, taskViewModelFactory).get(TaskViewModel::class.java)
        shareViewModel = ViewModelProvider(requireActivity()).get(ShareViewModel::class.java)

        taskViewModel.getTasksByCategory(safeargs.category)
        adapter = TaskAdapter(mTasks)

        observeSharedViewModel()
        observeTasksByCategory()
    }
}