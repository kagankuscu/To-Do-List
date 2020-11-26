package com.kagan.to_dolist.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kagan.to_dolist.R
import com.kagan.to_dolist.databinding.FragmentTaskBinding
import kotlin.random.Random

class TaskFragment : Fragment(R.layout.fragment_task) {

    val TAG = "TaskFragment"
    private lateinit var binding: FragmentTaskBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTaskBinding.bind(view)

        binding.btnAdd.setOnClickListener {
            addTask()
        }
    }

    private fun addTask() {
        var layout: View? = null
        when (Random.nextInt(0, 5)) {
            0 -> layout = layoutInflater.inflate(R.layout.task_personal, null, false)
            1 -> layout = layoutInflater.inflate(R.layout.task_meeting, null, false)
            2 -> layout = layoutInflater.inflate(R.layout.task_shopping, null, false)
            3 -> layout = layoutInflater.inflate(R.layout.task_study, null, false)
            4 -> layout = layoutInflater.inflate(R.layout.task_work, null, false)
        }

        layout?.let {
            binding.gLTask.addView(it)
        }
    }
}