package com.sdm.ecomileage.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentTransaction
import com.sdm.ecomileage.R
import com.sdm.ecomileage.databinding.ActivityMainBinding
import com.sdm.ecomileage.fragment.OnBoardFragment

class OnBoardActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val transaction: FragmentTransaction =
            getSupportFragmentManager().beginTransaction()
        // Developing:
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        transaction.replace(R.id.ar_container, OnBoardFragment())
        transaction.commit()




    }
    override fun onBackPressed() {

   /*     if (supportFragmentManager.fragments.any { it is OnBoardFragment}) {
            val transaction: FragmentTransaction =
                getSupportFragmentManager().beginTransaction()
            // Developing:
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            transaction.replace(R.id.ar_container, RegisterFragment())
            transaction.commit()
            return
        }
        else if (supportFragmentManager.fragments.any { it is RegisterFragment})
        {
           finish()
            return
        }

       else if (supportFragmentManager.fragments.any { it is LoginFragment})
        {
            val transaction: FragmentTransaction =
                getSupportFragmentManager().beginTransaction()
            // Developing:
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            transaction.replace(R.id.ar_container, RegisterFragment())
            transaction.commit()
            return
        }
        else if (supportFragmentManager.fragments.any { it is SignUpFragment})
        {
            val transaction: FragmentTransaction =
                getSupportFragmentManager().beginTransaction()
            // Developing:
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            transaction.replace(R.id.ar_container, RegisterFragment())
            transaction.commit()
            return
        }*/

    }


}