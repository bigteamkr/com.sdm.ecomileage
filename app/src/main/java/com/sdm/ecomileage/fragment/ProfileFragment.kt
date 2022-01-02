package com.ksif.ecomileage.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ksif.ecomileage.adapters.PostsAdapter
import com.ksif.ecomileage.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    // This for control the Fragmnt-Layout views:
    lateinit var binding: FragmentProfileBinding
    lateinit var adapter : PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the fragment layout:
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.rvHome.setLayoutManager(LinearLayoutManager(context))

        adapter = PostsAdapter(requireContext())

        binding.rvHome.adapter = adapter;




        return binding.root // Get the fragment layout root.


    }




}