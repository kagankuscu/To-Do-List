package com.kagan.to_dolist.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kagan.to_dolist.R
import com.kagan.to_dolist.databinding.FragmentToDoListBinding

class TodoListFragment:Fragment(R.layout.fragment_to_do_list) {

    private lateinit var binding: FragmentToDoListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentToDoListBinding.bind(view)

    }
}