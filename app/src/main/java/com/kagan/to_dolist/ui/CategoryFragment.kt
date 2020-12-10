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
import com.kagan.to_dolist.db.TaskDB
import com.kagan.to_dolist.enums.CategoryType
import com.kagan.to_dolist.models.Category
import com.kagan.to_dolist.models.Task
import com.kagan.to_dolist.repositories.CategoryRepository
import com.kagan.to_dolist.repositories.TaskRepository
import com.kagan.to_dolist.viewModels.CategoryViewModel
import com.kagan.to_dolist.viewModels.TaskViewModel
import com.kagan.to_dolist.viewModels.viewModelFactory.CategoryViewModelFactory
import com.kagan.to_dolist.viewModels.viewModelFactory.TaskViewModelFactory
import io.sentry.Sentry

class CategoryFragment : Fragment(R.layout.fragment_category) {

    private val TAG = "CategoryFragment"
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var layout: View
    private lateinit var db: CategoryDB
    private lateinit var taskDB: TaskDB
    private lateinit var repository: CategoryRepository
    private lateinit var taskRepository: TaskRepository
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var categoryViewModelFactory: CategoryViewModelFactory
    private lateinit var taskViewModelFactory: TaskViewModelFactory
    private lateinit var category: Category
    private lateinit var lCategory: Category
    private val args: CategoryFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoryBinding.bind(view)

        binding.tvName.text = args.name

        binding.btnAdd.setOnClickListener {
            addView()
        }

        categoryViewModel.getCategory().observe(viewLifecycleOwner, {
            if (it == null) {
                categoryViewModel.save(
                    Category(
                        1,
                        personal = false,
                        meeting = false,
                        shopping = false,
                        study = false,
                        work = false
                    )
                )
            } else {
                category = it
                clearView()
                setPersonalCard()
                setMeetingCard()
                setShoppingCard()
                setStudyCard()
                setWorkCard()
            }
        })
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
                })
            .setPositiveButton(
                getString(R.string.btn_add),
                DialogInterface.OnClickListener { _, _ ->
                    if (this::layout.isInitialized) {
                        try {
                            when (categoryArray) {
                                PERSONAL -> {
                                    if (!category.personal) {
                                        category.personal = true
                                    }
                                }
                                MEETING -> {
                                    if (!category.meeting) {
                                        category.meeting = true
                                    }
                                }
                                SHOPPING -> {
                                    if (!category.shopping) {
                                        category.shopping = true
                                    }
                                }
                                STUDY -> {
                                    if (!category.study) {
                                        category.study = true
                                    }
                                }
                                WORK -> {
                                    if (!category.work) {
                                        category.work = true
                                    }
                                }
                                else -> Log.d(TAG, "else")
                            }
                            Log.d(TAG, "addView: $category")
                            categoryViewModel.save(category)
                            lCategory = category
                        } catch (e: Exception) {
                            Sentry.captureMessage(e.message.toString())
                        }
                    }
                })
            .show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
    }

    private fun clearView() {
        binding.gLCategory.removeAllViews()
    }

    private fun setPersonalCard() {
        if (category.personal) {

            layout =
                layoutInflater.inflate(R.layout.category_personal, null, false)
            setTaskCountText(layout, CategoryType.PERSONAL)
            setCardViewClickListener()
            binding.gLCategory.addView(layout)
        }
    }

    private fun setMeetingCard() {
        if (category.meeting) {

            layout =
                layoutInflater.inflate(R.layout.category_meeting, null, false)
            setTaskCountText(layout, CategoryType.MEETING)
            setCardViewClickListener()
            binding.gLCategory.addView(layout)
        }
    }

    private fun setShoppingCard() {
        if (category.shopping) {

            layout =
                layoutInflater.inflate(R.layout.category_shopping, null, false)
            setTaskCountText(layout, CategoryType.SHOPPING)
            setCardViewClickListener()
            binding.gLCategory.addView(layout)
        }
    }

    private fun setStudyCard() {
        if (category.study) {

            layout =
                layoutInflater.inflate(R.layout.category_study, null, false)
            setTaskCountText(layout, CategoryType.STUDY)
            setCardViewClickListener()
            binding.gLCategory.addView(layout)
        }
    }

    private fun setWorkCard() {
        if (category.work) {

            layout =
                layoutInflater.inflate(R.layout.category_work, null, false)
            setTaskCountText(layout, CategoryType.WORK)
            setCardViewClickListener()
            binding.gLCategory.addView(layout)
        }
    }

    private fun setTaskCountText(layout: View, categoryType: CategoryType) {
        val taskCount = layout.findViewById<TextView>(R.id.tvTaskCount)
        taskViewModel.getTotalTaskByCategory(categoryType).observe(viewLifecycleOwner, {
            taskCount.text = getString(R.string.how_many_task, it)
        })
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = CategoryDB(requireContext())
        taskDB = TaskDB(requireContext())
        repository = CategoryRepository(db)
        taskRepository = TaskRepository(taskDB)
        categoryViewModelFactory = CategoryViewModelFactory(repository)
        taskViewModelFactory = TaskViewModelFactory(taskRepository)
        categoryViewModel =
            ViewModelProvider(this, categoryViewModelFactory).get(CategoryViewModel::class.java)
        taskViewModel =
            ViewModelProvider(this, taskViewModelFactory).get(TaskViewModel::class.java)

    }
}