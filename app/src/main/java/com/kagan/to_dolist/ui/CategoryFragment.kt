package com.kagan.to_dolist.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.kagan.to_dolist.R
import com.kagan.to_dolist.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment(R.layout.fragment_category) {

    private val TAG = "CategoryFragment"
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var layout: View


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoryBinding.bind(view)

        binding.btnAdd.setOnClickListener {
            addView()
        }
    }

    private fun addView() {

        AlertDialog.Builder(context)
            .setTitle("Category")
            .setSingleChoiceItems(R.array.categories,
                -1,
                DialogInterface.OnClickListener { _, which ->
                    Log.d(TAG, "addView: $which")
                    when (which) {
                        0 -> {
                            layout =
                                layoutInflater.inflate(R.layout.category_personal, null, false)
                        }
                    }
                })
            .setPositiveButton("Add", DialogInterface.OnClickListener { _, _ ->
                if (this::layout.isInitialized) {
                    binding.gLCategory.addView(layout)
                }
            })
            .show()


    }
}