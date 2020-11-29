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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kagan.to_dolist.R
import com.kagan.to_dolist.constants.Constant.MEETING
import com.kagan.to_dolist.constants.Constant.PERSONAL
import com.kagan.to_dolist.constants.Constant.SHOPPING
import com.kagan.to_dolist.constants.Constant.STUDY
import com.kagan.to_dolist.constants.Constant.WORK
import com.kagan.to_dolist.databinding.FragmentCategoryBinding
import com.kagan.to_dolist.viewModels.DataStoreViewModel
import io.sentry.Sentry

class CategoryFragment : Fragment(R.layout.fragment_category) {

    private val TAG = "CategoryFragment"
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var layout: View
    private lateinit var dataStoreViewModel: DataStoreViewModel
    private var saveCategory = mutableMapOf(
        PERSONAL to false,
        MEETING to false,
        SHOPPING to false,
        STUDY to false,
        WORK to false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoryBinding.bind(view)

        binding.btnAdd.setOnClickListener {
            addView()
        }
    }

    private fun addView() {

        var categoryArray = ""

        val alertDialog = AlertDialog.Builder(context)
            .setTitle(getString(R.string.add_category))
            .setSingleChoiceItems(R.array.categories,
                -1,
                DialogInterface.OnClickListener { dialog, which ->
                    dialog as AlertDialog
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
                    categoryArray = resources.getStringArray(R.array.categories)[which]

                    Log.d(TAG, "addView: ${saveCategory.entries}")

                    if (saveCategory[categoryArray] != true) {
                        setLayout(which)
                        setCardViewClickListener()
                    } else {
                        Toast.makeText(
                            context,
                            getString(R.string.category_already_exists),
                            Toast.LENGTH_SHORT
                        ).show()
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
                    }
                })
            .setPositiveButton(
                getString(R.string.btn_add),
                DialogInterface.OnClickListener { _, _ ->
                    if (this::layout.isInitialized) {
                        try {
                            binding.gLCategory.addView(layout)
                            saveCategory[categoryArray] = true
                        } catch (e: Exception) {
                            Sentry.captureMessage(e.message.toString())
                        }
                    }
                })
            .show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
    }

    private fun setLayout(which: Int? = -1) {
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
    }

    private fun setPersonalCard() {
        layout =
            layoutInflater.inflate(R.layout.category_personal, null, false)
        setCardViewClickListener()
        binding.gLCategory.addView(layout)
    }

    private fun setMeetingCard() {
        layout =
            layoutInflater.inflate(R.layout.category_meeting, null, false)
        setCardViewClickListener()
        binding.gLCategory.addView(layout)
    }

    private fun setShoppingCard() {
        layout =
            layoutInflater.inflate(R.layout.category_shopping, null, false)
        setCardViewClickListener()
        binding.gLCategory.addView(layout)
    }

    private fun setStudyCard() {
        layout =
            layoutInflater.inflate(R.layout.category_study, null, false)
        setCardViewClickListener()
        binding.gLCategory.addView(layout)
    }

    private fun setWorkCard() {
        layout =
            layoutInflater.inflate(R.layout.category_work, null, false)
        setCardViewClickListener()
        binding.gLCategory.addView(layout)
    }

    private fun setCardViewClickListener() {
        val text = layout.findViewById<TextView>(R.id.tvCategory).text

        layout.findViewById<CardView>(R.id.cardView).setOnClickListener {
            Toast.makeText(
                context,
                text,
                Toast.LENGTH_SHORT
            ).show()
            val action =
                CategoryFragmentDirections.actionTodoListFragmentToTaskFragment(text.toString())
            findNavController().navigate(action)
        }
    }

    private fun observeDataStore() {
        dataStoreViewModel.personal.observe(this, {
            saveCategory[PERSONAL] = it
            if (it)
                setPersonalCard()
        })

        dataStoreViewModel.meeting.observe(this, {
            saveCategory[MEETING] = it
            if (it)
                setMeetingCard()
        })

        dataStoreViewModel.shopping.observe(this, {
            saveCategory[SHOPPING] = it
            if (it)
                setShoppingCard()
        })

        dataStoreViewModel.study.observe(this, {
            saveCategory[STUDY] = it
            if (it)
                setStudyCard()
        })

        dataStoreViewModel.work.observe(this, {
            saveCategory[WORK] = it
            if (it)
                setWorkCard()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStoreViewModel = ViewModelProvider(this).get(DataStoreViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: $saveCategory")
        observeDataStore()
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: $saveCategory ")
        dataStoreViewModel.saveCategory(
            personal = saveCategory[PERSONAL]!!,
            meeting = saveCategory[MEETING]!!,
            shopping = saveCategory[SHOPPING]!!,
            study = saveCategory[STUDY]!!,
            work = saveCategory[WORK]!!
        )
    }
}