package com.example.toycalorie.ui.recommand

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.toycalorie.R
import com.example.toycalorie.base.BaseActivity
import com.example.toycalorie.databinding.ActivityRecommendBinding
import com.example.toycalorie.ui.adapter.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendActivity : BaseActivity<ActivityRecommendBinding>(R.layout.activity_recommend) {


    private val tabConfigurationStrategy =
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    @SuppressLint("WrongConstant")
    private fun initUi() {
        val list = listOf(Food1Fragment(), Food2Fragment(), Food3Fragment())

        val pagerAdapter = FragmentPagerAdapter(list, this)

        with(binding) {
            viewPager.adapter = pagerAdapter
            viewPager.offscreenPageLimit = 3
            viewPager.isUserInputEnabled = false

            TabLayoutMediator(tabLayout, viewPager, tabConfigurationStrategy).attach()

        }
    }
}