package com.kagan.to_dolist.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kagan.to_dolist.R
import com.kagan.to_dolist.databinding.FragmentOnboardBinding
import com.kagan.to_dolist.viewModels.DataStoreViewModel

class OnBoardFragment : Fragment(R.layout.fragment_onboard) {

    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private lateinit var binding: FragmentOnboardBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOnboardBinding.bind(view)

        binding.btnContinue.setOnClickListener {
            if (isEmptyEditView()) {
                Toast.makeText(context, "Please write your name.", Toast.LENGTH_SHORT).show()
            } else {
                val user = binding.evName.text.toString()
                saveUser(user)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getUser()
    }

    private fun saveUser(user: String) {
        dataStoreViewModel.saveUser(user)
        navigate(user)
    }

    private val TAG = "Onboard"
    private fun getUser() {
        // TODO: 02-Dec-20 it is gonna late for the navigate i'll leave it for now.
        dataStoreViewModel.user.observe(this, {
            if (it.isNotEmpty()) {
                navigate(it)
            }
        })
    }

    private fun isEmptyEditView(): Boolean {
        return binding.evName.text?.isEmpty()!!
    }

    private fun navigate(user: String) {
        val action = OnBoardFragmentDirections.actionOnBoardFragmentToTodoListFragment(user)
        findNavController().navigate(action)
    }
}