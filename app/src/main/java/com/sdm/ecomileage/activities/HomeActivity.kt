package com.sdm.ecomileage.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation

import com.sdm.ecomileage.databinding.ActivityHomeBinding


import com.sdm.ecomileage.R
import com.sdm.ecomileage.fragment.*


class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.setTitle("북가좌동")
        //Initializing

        binding.navBottom.add( MeowBottomNavigation.Model(1,R.drawable.nav_home))
        binding.navBottom.add( MeowBottomNavigation.Model(2,R.drawable.nav_search))
        binding.navBottom.add( MeowBottomNavigation.Model(3,R.drawable.ic_baseline_add_24))
        binding.navBottom.add( MeowBottomNavigation.Model(4,R.drawable.nav_learn))
        binding.navBottom.add( MeowBottomNavigation.Model(5,R.drawable.nav_profile))

      /*  binding.navBottom.setOnShowListener(new MeowBottomNavigation.ShowListener()
        {
            @Override
            public void onShowItem( MeowBottomNavigation.Model item )
            {

            }

        });*/
        binding.navBottom.setOnShowListener(MeowBottomNavigation.ShowListener { item ->

            var fragment: Fragment? = null
            when (item.id) {
                1 -> fragment = HomeFragment()
                2 -> fragment = SearchFragment()
                3 -> fragment = AddFragment()
                4 -> fragment = LearnFragment()
                5 -> fragment = ProfileFragment()
            }
            LoadFragment(fragment)
        })
        binding.navBottom.setCount(4,"10")
        binding.navBottom.show(3,true)

        binding.navBottom.setOnClickMenuListener(MeowBottomNavigation.ClickListener { item ->
           // Toast.makeText(this@HomeActivity,item.id.toString(),Toast.LENGTH_SHORT).show()
        })

        binding.navBottom.setOnReselectListener(MeowBottomNavigation.ReselectListener { item ->
          //8  Toast.makeText(this@HomeActivity,"Reselect--  "+item.id.toString(),Toast.LENGTH_SHORT).show()
        })
    }

    private fun LoadFragment(fragment: Fragment?)
    {
        //Replace Fragment
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.frame_layout_tap, fragment!!)
            .commit()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_home, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {


        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId)
        {
          /*  R.id.menu_location ->
            R.id.menu_notifaction->
            R.id.menu_tool-> */

        }
        return super.onOptionsItemSelected(item)
    }

}



