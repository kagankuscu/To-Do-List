package com.kagan.to_dolist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.kagan.to_dolist.R
import com.kagan.to_dolist.constants.Navigate
import com.kagan.to_dolist.enums.CategoryType
import com.kagan.to_dolist.models.CategoryTaskCount
import com.kagan.to_dolist.ui.CategoryFragmentDirections

class CategoryAdapter(
    private val categories: List<CategoryTaskCount>,
    private val navigate: Navigate
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.cardView)
        val imCategory: ImageView = view.findViewById(R.id.imCategory)
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
        val tvTaskCount: TextView = view.findViewById(R.id.tvTaskCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.apply {
            cardView.setOnClickListener {
                navigate(
                    categories[position].categoryType,
                    categories[position].categoryType.name
                )
            }
            imCategory.setImageResource(setImage(position))
            tvCategory.text = categories[position].categoryType.name
            tvTaskCount.text = categories[position].taskCount
        }
    }

    override fun getItemCount(): Int = categories.size

    private fun setImage(position: Int): Int {
        return when (categories[position].categoryType) {
            CategoryType.PERSONAL -> R.drawable.ic_personal
            CategoryType.MEETING -> R.drawable.ic_meeting
            CategoryType.SHOPPING -> R.drawable.ic_shopping
            CategoryType.STUDY -> R.drawable.ic_study
            CategoryType.WORK -> R.drawable.ic_work
            else -> -1
        }
    }

    private fun navigate(categoryType: CategoryType, categoryName: String) {
        navigate.navigate(categoryType, categoryName)
    }
}