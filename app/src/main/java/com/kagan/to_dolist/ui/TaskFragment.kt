package com.kagan.to_dolist.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.kagan.to_dolist.R
import com.kagan.to_dolist.constants.Constant.MEETING
import com.kagan.to_dolist.constants.Constant.PERSONAL
import com.kagan.to_dolist.constants.Constant.SHOPPING
import com.kagan.to_dolist.constants.Constant.STUDY
import com.kagan.to_dolist.constants.Constant.WORK
import com.kagan.to_dolist.databinding.FragmentTaskBinding
import kotlin.random.Random

class TaskFragment : Fragment(R.layout.fragment_task) {

    val TAG = "TaskFragment"
    private lateinit var binding: FragmentTaskBinding
    private val safeargs: TaskFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTaskBinding.bind(view)

        binding.tvName.text = safeargs.category

        binding.btnAdd.setOnClickListener {
            addTask()
        }
    }

    private fun addTask() {
        var layout: View? = null
        when (safeargs.category) {
            PERSONAL -> layout = layoutInflater.inflate(R.layout.task_personal, null, false)
            MEETING -> layout = layoutInflater.inflate(R.layout.task_meeting, null, false)
            SHOPPING -> layout = layoutInflater.inflate(R.layout.task_shopping, null, false)
            STUDY -> layout = layoutInflater.inflate(R.layout.task_study, null, false)
            WORK -> layout = layoutInflater.inflate(R.layout.task_work, null, false)
        }

        layout?.let {
            binding.gLTask.addView(it)
        }
    }
}