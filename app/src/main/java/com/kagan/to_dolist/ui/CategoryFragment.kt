package com.kagan.to_dolist.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kagan.to_dolist.R
import com.kagan.to_dolist.constants.Constant.MEETING
import com.kagan.to_dolist.constants.Constant.PERSONAL
import com.kagan.to_dolist.constants.Constant.SHOPPING
import com.kagan.to_dolist.constants.Constant.STUDY
import com.kagan.to_dolist.constants.Constant.WORK
import com.kagan.to_dolist.databinding.FragmentCategoryBinding
import com.kagan.to_dolist.db.CategoryDB
import com.kagan.to_dolist.repositories.CategoryRepository
import com.kagan.to_dolist.viewModels.CategoryViewModel
import com.kagan.to_dolist.viewModels.viewModelFactory.CategoryViewModelFactory
import io.sentry.Sentry

class CategoryFragment : Fragment(R.layout.fragment_category) {

    private val TAG = "CategoryFragment"
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var layout: View
    private lateinit var db: CategoryDB
    private lateinit var repository: CategoryRepository
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var categoryViewModelFactory: CategoryViewModelFactory
    private lateinit var saveCategory: Map<String, Boolean>
    private val args: CategoryFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoryBinding.bind(view)

        binding.tvName.text = args.name

        binding.btnAdd.setOnClickListener {
            addView()
        }

        categoryViewModel.getCategory().observe(viewLifecycleOwner, {
            saveCategory = it
            Log.d(TAG, "Observe: $it ")
        })
    }

    private fun addView() {

        var categoryArray = ""
        var whichCategory = -1

        val alertDialog = AlertDialog.Builder(context)
            .setTitle(getString(R.string.add_category))
            .setSingleChoiceItems(R.array.categories,
                -1,
                DialogInterface.OnClickListener { dialog, which ->
                    dialog as AlertDialog
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
                    categoryArray = resources.getStringArray(R.array.categories)[which]

                    if (saveCategory[categoryArray] != true) {
                        whichCategory = which
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
//                            binding.gLCategory.addView(layout)
                            val categoriesMap = saveCategory.toMutableMap()
                            categoriesMap[categoryArray] = true
                            categoryViewModel.saved(categoriesMap)
                            setLayout(whichCategory)
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
            0 -> setPersonalCard()
            1 -> setMeetingCard()
            2 -> setShoppingCard()
            3 -> setStudyCard()
            4 -> setWorkCard()
        }
    }

    private fun setPersonalCard() {
        if (saveCategory[PERSONAL] == true) {
            setIsEmpty()

            layout =
                layoutInflater.inflate(R.layout.category_personal, null, false)
            setCardViewClickListener()
            binding.gLCategory.addView(layout)
        }
    }

    private fun setMeetingCard() {
        if (saveCategory[MEETING] == true) {
            setIsEmpty()

            layout =
                layoutInflater.inflate(R.layout.category_meeting, null, false)
            setCardViewClickListener()
            binding.gLCategory.addView(layout)
        }
    }

    private fun setShoppingCard() {
        if (saveCategory[SHOPPING] == true) {
            setIsEmpty()

            layout =
                layoutInflater.inflate(R.layout.category_shopping, null, false)
            setCardViewClickListener()
            binding.gLCategory.addView(layout)
        }
    }

    private fun setStudyCard() {
        if (saveCategory[STUDY] == true) {
            setIsEmpty()

            layout =
                layoutInflater.inflate(R.layout.category_study, null, false)
            setCardViewClickListener()
            binding.gLCategory.addView(layout)
        }
    }

    private fun setWorkCard() {
        if (saveCategory[WORK] == true) {
            setIsEmpty()

            layout =
                layoutInflater.inflate(R.layout.category_work, null, false)
            setCardViewClickListener()
            binding.gLCategory.addView(layout)
        }
    }

    private fun setEmptyCard() {
        layout =
            layoutInflater.inflate(R.layout.empty_layout, GridLayout(context), false)

        val mainText = layout.findViewById<TextView>(R.id.mainText)
        val subText = layout.findViewById<TextView>(R.id.subText)

        mainText.text = getString(R.string.main_text, getString(R.string.category))
        subText.text = getString(R.string.sub_text, getString(R.string.category))

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

    private fun setIsEmpty() {
        if (categoryViewModel.isEmpty) {
            categoryViewModel.isEmpty = false
            binding.gLCategory.removeAllViews()
        }

        Log.d(TAG, "setIsEmpty: ${categoryViewModel.isEmpty}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = CategoryDB.getInstance()
        repository = CategoryRepository(db)
        categoryViewModelFactory = CategoryViewModelFactory(repository)
        categoryViewModel =
            ViewModelProvider(this, categoryViewModelFactory).get(CategoryViewModel::class.java)

        saveCategory = categoryViewModel.getCategory().value!!

    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "onStart: isEmpty:${categoryViewModel.isEmpty}")

        if (categoryViewModel.isEmpty) {
            setEmptyCard()
        } else {
            setPersonalCard()
            setMeetingCard()
            setShoppingCard()
            setStudyCard()
            setWorkCard()
        }
    }

    override fun onStop() {
        super.onStop()
        binding.gLCategory.removeAllViews()
    }
}