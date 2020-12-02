package com.kagan.to_dolist.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kagan.to_dolist.R
import com.kagan.to_dolist.databinding.FragmentOnboardBinding

class OnBoardFragment : Fragment(R.layout.fragment_onboard) {

    private lateinit var binding: FragmentOnboardBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOnboardBinding.bind(view)

        binding.btnContinue.setOnClickListener {
            if (isEmptyEditView()) {
                Toast.makeText(context, "Please write your name.", Toast.LENGTH_SHORT).show()
            } else {
                navigate()
            }
        }
    }

    private fun isEmptyEditView(): Boolean {
        return binding.evName.text?.isEmpty()!!
    }

    private fun navigate() {
        val action = OnBoardFragmentDirections.actionOnBoardFragmentToTodoListFragment()
        findNavController().navigate(action)
    }
}