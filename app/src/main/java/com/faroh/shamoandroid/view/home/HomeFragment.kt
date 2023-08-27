package com.faroh.shamoandroid.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.faroh.shamoandroid.R
import com.faroh.shamoandroid.core.ui.HomePagerAdapter
import com.faroh.shamoandroid.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private val tabTitles =
        arrayListOf("All Shoes", "Running", "Training", "BasketBall", "Hiking")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentHomeBinding.inflate(layoutInflater)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getUser().observe(requireActivity()) { user ->
            user?.let {
                homeBinding.apply {
                    tvHelloHome.text = getString(R.string.hello_home, it.name)
                    tvUsernameHome.text = getString(R.string.username_home, it.username)

                    Glide.with(requireActivity()).load(it.userProfilePhoto).circleCrop()
                        .into(ivProfileHome)
                }
            }
        }

        homeBinding.vpHome.adapter = HomePagerAdapter(this)
        TabLayoutMediator(homeBinding.tlHome, homeBinding.vpHome) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        for (i in 0..4) {
            val textView = LayoutInflater.from(requireContext()).inflate(R.layout.tab_title, null)
            homeBinding.tlHome.getTabAt(i)?.customView = textView
        }
    }
}