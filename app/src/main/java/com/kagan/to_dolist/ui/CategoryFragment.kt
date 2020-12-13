package com.kagan.to_dolist.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.kagan.to_dolist.R
import com.kagan.to_dolist.adapters.CategoryAdapter
import com.kagan.to_dolist.databinding.FragmentCategoryBinding
import com.kagan.to_dolist.db.CategoryDB
import com.kagan.to_dolist.db.TaskDB
import com.kagan.to_dolist.enums.CategoryType
import com.kagan.to_dolist.models.Category
import com.kagan.to_dolist.models.CategoryTaskCount
import com.kagan.to_dolist.repositories.CategoryRepository
import com.kagan.to_dolist.repositories.TaskRepository
import com.kagan.to_dolist.viewModels.CategoryViewModel
import com.kagan.to_dolist.viewModels.TaskViewModel
import com.kagan.to_dolist.viewModels.viewModelFactory.CategoryViewModelFactory
import com.kagan.to_dolist.viewModels.viewModelFactory.TaskViewModelFactory
import io.sentry.Sentry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch

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
    private lateinit var adapter: CategoryAdapter
    private lateinit var categoriesTaskCount: ArrayList<CategoryTaskCount>
    private val args: CategoryFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoryBinding.bind(view)

        binding.tvName.text = args.name

        binding.btnAdd.setOnClickListener {
            addView()
        }

        categoryViewModel.getCategory().observe(viewLifecycleOwner, {
            categoriesTaskCount.clear()
            it.forEach { cat ->
                categoriesTaskCount.add(CategoryTaskCount(cat.categoryType))
            }
            setTaskCountText()
        })

        binding.rvCategory.recyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.rvCategory.recyclerView.adapter = adapter
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

                            Log.d(TAG, "addView: $category")
                            categoryViewModel.save(category)

                        } catch (e: Exception) {
                            Sentry.captureMessage(e.message.toString())
                        }
                    }
                })
            .show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
    }

    private fun setTaskCountText() {
        categoriesTaskCount.forEach {
            var count = -1
            val job = GlobalScope.launch(IO) {
                count = taskViewModel.getTotalTaskByCategory(it.categoryType)
            }
            GlobalScope.launch(Main) {
                job.join()
                it.taskCount = getString(R.string.how_many_task, count)
                adapter.notifyDataSetChanged()
            }
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

        categoriesTaskCount = arrayListOf()
        adapter = CategoryAdapter(categoriesTaskCount)

    }
}