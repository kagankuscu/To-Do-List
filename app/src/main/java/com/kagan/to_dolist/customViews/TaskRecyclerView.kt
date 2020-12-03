package com.kagan.to_dolist.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.kagan.to_dolist.R
import com.kagan.to_dolist.databinding.EmptyLayoutBinding
import com.kagan.to_dolist.databinding.LceRecyclerViewBinding

class TaskRecyclerView(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    private val binding: LceRecyclerViewBinding =
        LceRecyclerViewBinding.inflate(LayoutInflater.from(context), this)

    private val emptyBinding: EmptyLayoutBinding

    val recyclerView: RecyclerView
        get() = binding.recyclerView

    var mainText: String = ""
        set(value) {
            field = value
            emptyBinding.mainText.text = resources.getString(R.string.main_text, value)
        }

    var subText: String = ""
        set(value) {
            field = value
            emptyBinding.subText.text = resources.getString(R.string.sub_text, value)
        }

    init {
        emptyBinding = binding.emptyView

        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.TaskRecyclerView,
            0, 0
        ).apply {
            try {
                mainText = getString(R.styleable.TaskRecyclerView_mainText) ?: "Nothing to show"
                subText =
                    getString(R.styleable.TaskRecyclerView_subText) ?: "Nothing to show subtext"
            } finally {
                recycle()
            }
        }
    }

    fun showEmptyView(msg: String? = null) {
        mainText = msg ?: mainText
        emptyBinding.root.visibility = View.VISIBLE
    }

    fun hideEmptyView() {
        emptyBinding.root.visibility = View.GONE
    }
}