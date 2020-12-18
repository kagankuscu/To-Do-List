package com.kagan.to_dolist.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kagan.to_dolist.R
import com.kagan.to_dolist.adapters.TaskAdapter
import com.kagan.to_dolist.constants.SetTaskOnClickListener
import com.kagan.to_dolist.databinding.FragmentTaskBinding
import com.kagan.to_dolist.db.TaskDB
import com.kagan.to_dolist.models.Task
import com.kagan.to_dolist.repositories.TaskRepository
import com.kagan.to_dolist.utils.SwipeToDeleteCallBack
import com.kagan.to_dolist.utils.SwipeToEditCallBack
import com.kagan.to_dolist.viewModels.ShareViewModel
import com.kagan.to_dolist.viewModels.TaskViewModel
import com.kagan.to_dolist.viewModels.viewModelFactory.TaskViewModelFactory

class TaskFragment : Fragment(R.layout.fragment_task), SetTaskOnClickListener {

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
    private lateinit var swipeHandler: SwipeToDeleteCallBack
    private lateinit var swipeEdit: SwipeToEditCallBack


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTaskBinding.bind(view)

        binding.tvName.text = safeargs.category.name
        binding.taskRecyclerView.recyclerView.adapter = adapter
        binding.btnAdd.setOnClickListener {
            navigateToAddTask()
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.taskRecyclerView.recyclerView)


        val itemEdit = ItemTouchHelper(swipeEdit)
        itemEdit.attachToRecyclerView(binding.taskRecyclerView.recyclerView)
    }

    private fun observeTasksByCategory(): Unit {
        taskViewModel.getTasksByCategory(safeargs.category).observe(this, {
            val oldSize = mTasks.size
            mTasks.clear()
            mTasks.addAll(it)
            if (oldSize != mTasks.size) {
                binding.taskRecyclerView.recyclerView.scrollToPosition(mTasks.size - 1)
                if (it.isNotEmpty()) {
                    adapter.notifyItemInserted(mTasks.size - 1)
                    binding.taskRecyclerView.hideEmptyView()
                } else {
                    binding.taskRecyclerView.showEmptyView()
                }
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
        adapter = TaskAdapter(mTasks, this)
        swipeHandler = object : SwipeToDeleteCallBack(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Log.d(TAG, "onSwiped: ${mTasks[viewHolder.adapterPosition]}")
                deleteTask(viewHolder)
            }
        }
        swipeEdit = object : SwipeToEditCallBack(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Toast.makeText(context, "Edit", Toast.LENGTH_SHORT).show()
                navigateToEditFragment(viewHolder)
            }
        }
        observeSharedViewModel()
        observeTasksByCategory()

    }

    private fun getTaskById(viewHolder: RecyclerView.ViewHolder) =
        mTasks[viewHolder.adapterPosition]


    private fun deleteTask(viewHolder: RecyclerView.ViewHolder) {
        val task = getTaskById(viewHolder)
        task.isDeleted = true
        mTasks.removeAt(viewHolder.adapterPosition)
        adapter.notifyItemRemoved(viewHolder.adapterPosition)
        taskViewModel.delete(task)
        showSnackBar(task)
    }

    private fun navigateToEditFragment(viewHolder: RecyclerView.ViewHolder) {
        val task = getTaskById(viewHolder)
        shareViewModel.setTaskToNew(task)
        val action = TaskFragmentDirections.actionTaskFragmentToNewTaskFragment(safeargs.category)
        findNavController().navigate(action)
    }

    override fun onItemClick(position: Int, task: Task) {
        Toast.makeText(
            context,
            "OnClickListener $position isCompleted=${task.isCompleted}",
            Toast.LENGTH_SHORT
        ).show()
        task.isCompleted = true
        taskViewModel.completed(task)
        adapter.notifyItemChanged(position)
    }

    private fun showSnackBar(task: Task) {
        Snackbar.make(
            requireView(),
            "Task Deleted.",
            Snackbar.LENGTH_SHORT
        )
            .setAction("Restore", View.OnClickListener {
                task.isDeleted = false
                taskViewModel.restore(task)
            })
            .show()
    }
}