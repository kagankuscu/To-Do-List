package com.kagan.to_dolist.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.kagan.to_dolist.R
import com.kagan.to_dolist.constants.SetTaskOnClickListener
import com.kagan.to_dolist.constants.SimpleDateFormat.formatTime
import com.kagan.to_dolist.enums.CategoryType
import com.kagan.to_dolist.models.Task
import java.util.*

class TaskAdapter(
    private val tasks: ArrayList<Task>,
    private val listener: SetTaskOnClickListener
) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    private val TAG = "TaskAdapter"

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val cardView: CardView = view.findViewById(R.id.cardView)
        var ivCategoryColor: ImageView = view.findViewById(R.id.ivCategoryColor)
        var tvTime: TextView = view.findViewById(R.id.tvTime)
        var tvTaskDesc: TextView = view.findViewById(R.id.tvTaskDesc)
        var ivCheck: ImageView = view.findViewById(R.id.ivCheck)

        init {
            ivCheck.setOnClickListener {
                val position = adapterPosition
                listener.onItemClick(position, tasks[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            val isCheck = tasks[position].isCompleted
            ivCategoryColor.setImageResource(setImage(position))
            tvTime.text = formatTime(tasks[position].dueDateTime)
            tvTaskDesc.text = tasks[position].title

            ivCheck.setImageResource(R.drawable.ic_radio_button_unchecked)
            if (isCheck) {
                ivCheck.setImageResource(R.drawable.ic_checked_circle)
            }

        }
    }

    override fun getItemCount(): Int = tasks.size

    private fun setImage(position: Int): Int {
        return when (tasks[position].categoryType) {
            CategoryType.PERSONAL -> R.drawable.task_personal
            CategoryType.MEETING -> R.drawable.task_meeting
            CategoryType.SHOPPING -> R.drawable.task_shopping
            CategoryType.STUDY -> R.drawable.task_study
            CategoryType.WORK -> R.drawable.task_work
            else -> R.drawable.task_unknown
        }
    }
}