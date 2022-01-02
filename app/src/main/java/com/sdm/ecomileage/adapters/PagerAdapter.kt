package com.ksif.ecomileage.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ksif.ecomileage.fragment.RegisterFragment
import com.ksif.ecomileage.fragment.LoginFragment
import java.util.ArrayList

class PagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    // Fields:
    private val fragments: MutableList<Fragment> = ArrayList()
    private val headers: MutableList<String> = ArrayList()
    private fun initData() {
        //init Fragments
        //init Fragments
        addData(LoginFragment(), "비밀번호 찾기")
        addData(RegisterFragment(),"아이디 찾기" )
    }

    private fun addData(fragment: Fragment, header: String) {
        //add Fragment
        fragments.add(fragment)
        //add header
        headers.add(header)
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    fun getHeader(position: Int): String {
        return headers[position]
    }

    init {
        initData()
    }
}