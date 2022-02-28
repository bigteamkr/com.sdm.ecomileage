package com.sdm.ecomileage.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sdm.ecomileage.activities.HomeActivity
import com.sdm.ecomileage.activities.OnBoardActivity
import com.sdm.ecomileage.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    // This for control the Fragment-Layout views:
    lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the fragment layout:
        binding = FragmentLoginBinding.inflate(inflater, container, false)


        binding.cardLogin.setOnClickListener {  }

        return binding.root // Get the fragment layout root.
    }

    private fun goToHome()
    {
        startActivity(Intent(context, HomeActivity::class.java ))

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