package com.sdm.ecomileage.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sdm.ecomileage.adapters.PostsAdapter
import com.sdm.ecomileage.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    // This for control the Fragmnt-Layout views:
    lateinit var binding: FragmentSearchBinding
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
        binding = FragmentSearchBinding.inflate(inflater, container, false)




        return binding.root // Get the fragment layout root.


    }




}