package com.sdm.ecomileage.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.sdm.ecomileage.R
import com.sdm.ecomileage.activities.HomeActivity
import com.sdm.ecomileage.adapters.BoardAdapter
import com.sdm.ecomileage.databinding.FragmentOnBoardBinding
import java.util.*

class OnBoardFragment : Fragment() {
    // This for control the Fragment-Layout views:
    lateinit var binding: FragmentOnBoardBinding

    // ViewPager:
    lateinit var adapter: BoardAdapter
    lateinit var boards: MutableList<Int>
    lateinit var textViews: Array<TextView?>
    private var index = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout:
        binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        return binding.root // Get the fragment layout root.
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initializing:
        initUI()
        initPager()
        // Developing:
        binding.nextBoardLayout.setOnClickListener { view: View -> moveToNextPage(view) }
        binding.onBoardPager.adapter = adapter
        binding.onBoardPager.registerOnPageChangeCallback(ARPagerCallBack())
    }

    // Initializing User Interface:
    private fun initUI() {
        // Initializing:
        val decorView = requireActivity().window.decorView
        // Developing:
        decorView.systemUiVisibility = View.FOCUSABLES_ALL
    }

    // OurPagerCallBack(Class):
    internal inner class ARPagerCallBack : ViewPager2.OnPageChangeCallback() {
        // Fields:
        private val request: ActivityResultContracts.RequestPermission =
            ActivityResultContracts.RequestPermission()


        @RequiresApi(Build.VERSION_CODES.M)
        override fun onPageSelected(position: Int) {
            // Setting index for control:
            index = position
            // CheckingDots:
            for (i in textViews.indices) {
                if (i == position) {
                    textViews[position]?.textSize = 20f
                    textViews[position]!!.setTextColor(Color.BLACK)
                } else {
                    textViews[i]!!.textSize = 15f
                    textViews[i]!!.setTextColor(requireContext().getColor(R.color.gray_light))
                }
            }

            if (position == 1) {
                enabled(false)
            }

            if (position == 2) {
                enabled(true)
                binding.nextBoardLayout.visibility = View.VISIBLE;
            }
            // Super:*/
            super.onPageSelected(position)
        }


        // Disable&EnableMethod:
        private fun enabled(state: Boolean) {
            binding.onBoardPager.isEnabled = state
        }
    }

    // Moving to next page (ButtonMethod):
    private fun moveToNextPage(view: View) {
        if (index < textViews.size) {
            index++
            binding.onBoardPager.currentItem = index
        }
        else
        {
            startActivity(Intent( requireActivity(),HomeActivity::class.java))
            requireActivity().finish()
        }
    }

    // InitializingPager:
    @RequiresApi(Build.VERSION_CODES.M)
    private fun initPager() {

        // Initializing:
        boards = ArrayList<Int>()
        // AddingContent:
        boards.add(R.drawable.card_1)
        boards.add(R.drawable.card_2)

        boards.add(R.drawable.card_3)

        // Initializing(Adapter & Texts):
        textViews = arrayOfNulls(boards.size)
        adapter = BoardAdapter(requireContext(), boards)
        // Dots:
        for (i in boards.indices) {
            // Initializing:
            textViews[i] = TextView(requireContext())
            // SettingAttrs:
            textViews[i]?.text = Html.fromHtml("&#9679;")
            textViews[i]!!.textSize = 20f
            textViews[i]!!.setTextColor(requireContext().getColor(R.color.gray_light))
            textViews[i]!!.setPadding(8, 0, 8, 0)
            // Finishing:
            binding.dotsLayout.addView(textViews[i])
        }



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            android.R.id.home -> {


                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
