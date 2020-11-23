package com.kagan.to_dolist.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.kagan.to_dolist.R
import com.kagan.to_dolist.databinding.FragmentCategoryBinding
import io.sentry.Sentry

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

        val l = AlertDialog.Builder(context)
            .setTitle(getString(R.string.add_category))
            .setSingleChoiceItems(R.array.categories,
                -1,
                DialogInterface.OnClickListener { dialog, which ->
                    val d = dialog as AlertDialog
                    d.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true

                    when (which) {
                        0 -> {
                            layout =
                                layoutInflater.inflate(R.layout.category_personal, null, false)
                        }
                        1 -> {
                            layout =
                                layoutInflater.inflate(R.layout.category_work, null, false)
                        }
                        2 -> layout =
                            layoutInflater.inflate(R.layout.category_meeting, null, false)
                        3 -> layout =
                            layoutInflater.inflate(R.layout.category_shopping, null, false)
                        4 -> layout =
                            layoutInflater.inflate(R.layout.category_study, null, false)
                    }
                    setCardViewClickListener()
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

    private fun setCardViewClickListener() {
        val text = layout.findViewById<TextView>(R.id.tvCategory).text

        layout.findViewById<CardView>(R.id.cardView).setOnClickListener {
            Log.d(TAG, "addView: $layout")
            Toast.makeText(
                context,
                text,
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: 23-Nov-20 datastore read function
    }

    override fun onStop() {
        super.onStop()
        // TODO: 23-Nov-20 datastore write function
    }
}