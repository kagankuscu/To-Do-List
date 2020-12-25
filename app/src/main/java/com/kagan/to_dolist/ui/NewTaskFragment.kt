package com.kagan.to_dolist.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kagan.to_dolist.R
import com.kagan.to_dolist.constants.SimpleDateFormat
import com.kagan.to_dolist.databinding.FragmentNewTaskBinding
import com.kagan.to_dolist.db.TaskDB
import com.kagan.to_dolist.models.Task
import com.kagan.to_dolist.repositories.TaskRepository
import com.kagan.to_dolist.viewModels.ShareViewModel
import com.kagan.to_dolist.viewModels.TaskViewModel
import com.kagan.to_dolist.viewModels.viewModelFactory.TaskViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewTaskFragment : Fragment(R.layout.fragment_new_task) {

    private val TAG = "NewTaskFragment"
    private lateinit var binding: FragmentNewTaskBinding
    private lateinit var shareViewModel: ShareViewModel
    private lateinit var db: TaskDB
    private lateinit var repository: TaskRepository
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskViewModelFactory: TaskViewModelFactory
    private val safeArgs: NewTaskFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewTaskBinding.bind(view)
        if (safeArgs.taskId != 0L) {
            CoroutineScope(IO).launch {
                val task = taskViewModel.getTaskById(safeArgs.taskId)

                withContext(Main) {
                    binding.etTask.setText(task.title)
                    binding.tvChooseDateShow.text = getString(
                        R.string.date_time_choosen,
                        task.dueDate,
                        task.dueTime
                    )
                }
            }
        }

        setOnclickListener()
        binding.etTask.setOnClickListener {
            binding.etTask.setBackgroundResource(R.drawable.new_task_edit_text_background)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shareViewModel = ViewModelProvider(requireActivity()).get(ShareViewModel::class.java)
        db = TaskDB(requireContext())
        repository = TaskRepository(db)
        taskViewModelFactory = TaskViewModelFactory(repository)
        taskViewModel = ViewModelProvider(this, taskViewModelFactory).get(TaskViewModel::class.java)
    }

    private fun setOnclickListener() {
        binding.lChooseDate.setOnClickListener {
            binding.lChooseDate.setBackgroundResource(R.drawable.new_task_edit_text_background)
            binding.tvTimeError.visibility = View.GONE
            binding.tvChooseDateShow.text = getString(
                R.string.date_time_choosen,
                SimpleDateFormat.formatDate(System.currentTimeMillis()),
                SimpleDateFormat.formatTime(System.currentTimeMillis())
            )
        }

        binding.btnAddTask.setOnClickListener {
            var isEmpty: Boolean = false

            if (binding.etTask.text.isEmpty()) {
                binding.etTask.error =
                    getString(R.string.new_task_error_message, getString(R.string.Title))
                isEmpty = true
                binding.etTask.setBackgroundResource(R.drawable.new_task_et_error_stroke)
            }

            if (binding.tvChooseDateShow.text.isEmpty()) {
                binding.lChooseDate.setBackgroundResource(R.drawable.new_task_et_error_stroke)
                binding.tvTimeError.visibility = View.VISIBLE
                binding.tvTimeError.text =
                    getString(R.string.new_task_error_message, getString(R.string.date_time))
                isEmpty = true
            }

            if (!isEmpty) {
                Log.d(TAG, "setOnclickListener: $isEmpty")
                if (safeArgs.taskId == 0L) {
                    addUpdateTask()
                } else {
                    addUpdateTask(safeArgs.taskId)
                }
                hideTheKeyboard()
                navigateBackToTaskFragment()
            }
        }
    }

    private fun navigateBackToTaskFragment() {
        findNavController().navigateUp()
    }

    private fun addUpdateTask(taskId: Long? = null) {
        var addTask: Task? = null

        val title = binding.etTask.text.toString()
        val dateStr = binding.tvChooseDateShow.text.toString()
        val date = SimpleDateFormat.formatDate(System.currentTimeMillis())
        val time = SimpleDateFormat.formatTime(System.currentTimeMillis())
        val category = safeArgs.category

        if (taskId == null) {
            addTask = Task(0, title, category, date, time)
            taskViewModel.saveTask(addTask)
        } else {
            val updatedTask: Task = Task(taskId, title, category, date, time)
            taskViewModel.updateTask(updatedTask)
        }
    }

    private fun hideTheKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}