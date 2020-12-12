package com.kagan.to_dolist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kagan.to_dolist.R
import com.kagan.to_dolist.models.Category

class CategoryAdapter(private val categories: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imCategory: ImageView = view.findViewById(R.id.imCategory)
        var tvCategory: TextView = view.findViewById(R.id.tvCategory)
        var tvTaskCount: TextView = view.findViewById(R.id.tvTaskCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.apply {
            imCategory.setImageResource(setImage())
        }
    }

    override fun getItemCount(): Int = categories.size


    private fun setImage(): Int {
        return 0
    }
}