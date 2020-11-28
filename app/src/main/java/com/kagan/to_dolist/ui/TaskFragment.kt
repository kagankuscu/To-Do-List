package com.kagan.to_dolist.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kagan.to_dolist.R
import com.kagan.to_dolist.constants.Constant.MEETING
import com.kagan.to_dolist.constants.Constant.PERSONAL
import com.kagan.to_dolist.constants.Constant.SHOPPING
import com.kagan.to_dolist.constants.Constant.STUDY
import com.kagan.to_dolist.constants.Constant.WORK
import com.kagan.to_dolist.databinding.FragmentTaskBinding

class TaskFragment : Fragment(R.layout.fragment_task) {

    val TAG = "TaskFragment"
    private lateinit var binding: FragmentTaskBinding
    private val safeargs: TaskFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTaskBinding.bind(view)

        binding.tvName.text = safeargs.category

        binding.btnAdd.setOnClickListener {
            navigateToAddTask()
        }
    }

    private fun navigateToAddTask() {
        findNavController().navigate(R.id.action_taskFragment_to_newTaskFragment)
    }
}