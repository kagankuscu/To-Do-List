package com.kagan.to_dolist.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.kagan.to_dolist.R
import com.kagan.to_dolist.databinding.FragmentCategoryBinding
import io.sentry.Sentry

class CategoryFragment : Fragment(R.layout.fragment_category) {

    private val TAG = "CategoryFragment"
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var layout: View
    private var layoutId = -1


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoryBinding.bind(view)

        binding.btnAdd.setOnClickListener {
            addView()
        }
    }

    private fun addView() {

        val l = AlertDialog.Builder(context)
            .setTitle(getString(R.string.add_category))
            .setSingleChoiceItems(R.array.categories,
                -1,
                DialogInterface.OnClickListener { dialog, which ->
                    val d = dialog as AlertDialog
                    d.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true

                    when (which) {
                        0 -> layout =
                            layoutInflater.inflate(R.layout.category_personal, null, false)
                        1 -> layout =
                            layoutInflater.inflate(R.layout.category_work, null, false)
                        2 -> layout =
                            layoutInflater.inflate(R.layout.category_meeting, null, false)
                        3 -> layout =
                            layoutInflater.inflate(R.layout.category_shopping, null, false)
                        4 -> layout =
                            layoutInflater.inflate(R.layout.category_study, null, false)
                    }
                })
            .setPositiveButton(
                getString(R.string.btn_add),
                DialogInterface.OnClickListener { _, _ ->
                    if (this::layout.isInitialized) {
                        try {
                            binding.gLCategory.addView(layout)
                        } catch (e: Exception) {
                            Sentry.captureMessage(e.message.toString())
                        }
                    }
                })
            .show()

        l.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
    }
}