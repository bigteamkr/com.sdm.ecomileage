package com.ksif.ecomileage.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksif.ecomileage.adapters.UsersAdapter
import com.ksif.ecomileage.databinding.FragmentAddBinding


class AddFragment : Fragment() {

    // This for control the Fragmnt-Layout views:
    lateinit var binding: FragmentAddBinding
    lateinit var adapter : UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the fragment layout:
        binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.rvAdd.setLayoutManager(LinearLayoutManager(context))

        adapter = UsersAdapter(requireContext())

        binding.rvAdd.adapter = adapter;



        return binding.root // Get the fragment layout root.


    }




}