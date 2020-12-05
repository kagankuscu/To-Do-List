package com.kagan.to_dolist.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kagan.to_dolist.R
import com.kagan.to_dolist.constants.SimpleDateFormat
import com.kagan.to_dolist.databinding.FragmentNewTaskBinding
import com.kagan.to_dolist.enums.Category
import com.kagan.to_dolist.models.Task
import com.kagan.to_dolist.viewModels.ShareViewModel

class NewTaskFragment : Fragment(R.layout.fragment_new_task) {

    private val TAG = "NewTaskFragment"
    private lateinit var binding: FragmentNewTaskBinding
    private lateinit var shareViewModel: ShareViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewTaskBinding.bind(view)
        setOnclickListener()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shareViewModel = ViewModelProvider(requireActivity()).get(ShareViewModel::class.java)
    }

    private fun setOnclickListener() {
        binding.lChooseDate.setOnClickListener {
            binding.tvChooseDateShow.text = SimpleDateFormat.formatTime(System.currentTimeMillis())
        }

        binding.btnAddTask.setOnClickListener {
            shareViewModel.setTask(addTask())
        }
    }

    private fun addTask(): Task {
        val title = binding.etTask.text.toString()
        val date = binding.tvChooseDateShow.text.toString()
        val dateSplit = date.split(" ")[0]
        val dateLong = System.currentTimeMillis()
        val category = Category.PERSONAL

        return Task(title, category, dateLong)
    }
}