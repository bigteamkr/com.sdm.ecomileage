package com.ksif.ecomileage.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ksif.ecomileage.activities.HomeActivity
import com.ksif.ecomileage.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    // This for control the Fragment-Layout views:
    lateinit var binding: FragmentRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the fragment layout:
        binding = FragmentRegisterBinding.inflate(inflater, container, false)



        binding.cardGoogle.setOnClickListener { goToHome() }
        binding.cardApple.setOnClickListener { goToHome() }
        binding.cardFacebook.setOnClickListener { goToHome() }
        binding.cardLogin.setOnClickListener { goToHome() }
        binding.cardKokaoTalk.setOnClickListener { goToHome() }
        binding.cardNaver.setOnClickListener { goToHome() }



        return binding.root // Get the fragment layout root.


    }

    private fun goToHome()
    {
      startActivity(Intent(context,HomeActivity::class.java ))
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}