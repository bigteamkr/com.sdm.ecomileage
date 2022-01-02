package com.sdm.ecomileage.activities

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.sdm.ecomileage.adapters.PagerAdapter
import com.sdm.ecomileage.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    var binding: ActivityRegisterBinding? = null

    //TabLayoutMediator
    var mediator: TabLayoutMediator? = null

    //Adapter
    var adapter: PagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater) // INFLATE THE LAYOUT.
        val view: View = binding!!.root // GET ROOT [BY DEF(CONSTRAINT LAYOUT)].
        setContentView(view) // SET THE VIEW CONTENT TO THE (VIEW).

        //Initializing
        adapter = PagerAdapter(supportFragmentManager, lifecycle)
        mediator = TabLayoutMediator(
            binding!!.tabLayout,
            binding!!.viewPager
        ) { tab: TabLayout.Tab, position: Int -> tab.text = adapter!!.getHeader(position) }

        //setAdapter
        binding!!.viewPager.adapter = adapter

        //AttachMediator
        if (!mediator!!.isAttached) mediator!!.attach()
    }


}