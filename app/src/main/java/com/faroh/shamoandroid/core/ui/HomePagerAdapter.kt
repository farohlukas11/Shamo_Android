package com.faroh.shamoandroid.core.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.faroh.shamoandroid.view.home.all_shoes.AllShoesFragment
import com.faroh.shamoandroid.view.home.basketball.BasketBallFragment
import com.faroh.shamoandroid.view.home.hiking.HikingFragment
import com.faroh.shamoandroid.view.home.running.RunningFragment
import com.faroh.shamoandroid.view.home.training.TrainingFragment

class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllShoesFragment()
            1 -> RunningFragment()
            2 -> TrainingFragment()
            3 -> BasketBallFragment()
            else -> HikingFragment()
        }
    }
}