package com.kagan.to_dolist.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kagan.to_dolist.R
import com.kagan.to_dolist.constants.SimpleDateFormat
import com.kagan.to_dolist.databinding.FragmentNewTaskBinding
import com.kagan.to_dolist.models.Task
import com.kagan.to_dolist.viewModels.ShareViewModel

class NewTaskFragment : Fragment(R.layout.fragment_new_task) {

    private val TAG = "NewTaskFragment"
    private lateinit var binding: FragmentNewTaskBinding
    private lateinit var shareViewModel: ShareViewModel
    private val safeArgs: NewTaskFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewTaskBinding.bind(view)
        setOnclickListener()
        binding.etTask.setOnClickListener {
            binding.etTask.setBackgroundResource(R.drawable.new_task_edit_text_background)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shareViewModel = ViewModelProvider(requireActivity()).get(ShareViewModel::class.java)
    }

    private fun setOnclickListener() {
        binding.lChooseDate.setOnClickListener {
            binding.lChooseDate.setBackgroundResource(R.drawable.new_task_edit_text_background)
            binding.tvChooseDateShow.text = SimpleDateFormat.formatTime(System.currentTimeMillis())
        }

        binding.btnAddTask.setOnClickListener {
            var isEmpty: Boolean = false

            if (binding.etTask.text.isEmpty()) {
                binding.etTask.error =
                    getString(R.string.new_task_error_message, getString(R.string.title))
                isEmpty = true
                binding.etTask.setBackgroundResource(R.drawable.new_task_et_error_stroke)
            }

            if (binding.tvChooseDateShow.text.isEmpty()) {
                binding.lChooseDate.setBackgroundResource(R.drawable.new_task_et_error_stroke)
                isEmpty = true
            }

            if (!isEmpty) {
                shareViewModel.setTask(addTask())
                navigateBackToTaskFragment()
            }
        }
    }

    private fun navigateBackToTaskFragment() {
        findNavController().navigateUp()
    }

    private fun addTask(): Task {
        val title = binding.etTask.text.toString()
        val date = binding.tvChooseDateShow.text.toString()
        val dateLong = System.currentTimeMillis()
        val category = safeArgs.category

        return Task(title, category, dateLong)
    }
}