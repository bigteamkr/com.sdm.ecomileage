package com.sdm.ecomileage.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sdm.ecomileage.fragment.HomeFragment

import java.util.ArrayList

class PagerHomeAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    // Fields:
    private val fragments: MutableList<Fragment> = ArrayList()
    private val headers: MutableList<String> = ArrayList()
    private fun initData() {
        //init Fragments
        //init Fragments
        addData(HomeFragment(), "Home")
        addData(HomeFragment(), "Home2")
        addData(HomeFragment(), "Home3")
        addData(HomeFragment(), "Home4")
        addData(HomeFragment(), "Home5")
    }

    private fun addData(fragment: Fragment, header: String) {
        //add Fragment
        fragments.add(fragment)
        //add header
        headers.add(header)
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */

    override fun getItemCount(): Int
    {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }


    fun getHeader(position: Int): String {
        return headers[position]
    }

    init {
        initData()
    }
}