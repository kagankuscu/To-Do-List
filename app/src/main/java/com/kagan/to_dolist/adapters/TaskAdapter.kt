package com.kagan.to_dolist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kagan.to_dolist.R
import com.kagan.to_dolist.constants.SimpleDateFormat.formatTime
import com.kagan.to_dolist.models.Task
import java.text.SimpleDateFormat
import java.util.*

class TaskAdapter(private val tasks: ArrayList<Task>) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvTime: TextView = view.findViewById(R.id.tvTime)
        var tvTaskDesc: TextView = view.findViewById(R.id.tvTaskDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_personal, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            tvTime.text = formatTime(tasks[position].dueDateTime)
            tvTaskDesc.text = tasks[position].title
        }
    }

    override fun getItemCount(): Int = tasks.size
}