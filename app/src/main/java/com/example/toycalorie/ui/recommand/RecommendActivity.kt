package com.example.toycalorie.ui.recommand

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.toycalorie.R
import com.example.toycalorie.base.BaseActivity
import com.example.toycalorie.databinding.ActivityRecommendBinding
import com.example.toycalorie.ui.adapter.FragmentPagerAdapter
import com.example.toycalorie.ui.calculate.CalculateViewModel
import com.example.toycalorie.ui.grocery.GroceryActivity
import com.example.toycalorie.util.CalorieUtil
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendActivity : BaseActivity<ActivityRecommendBinding>(R.layout.activity_recommend) {

    private val recommendViewModel: RecommendViewModel by viewModels()

    private var getSelectGroceryList: ArrayList<String>? = null
    private var totalCalorie: Double? = null

    private val tabConfigurationStrategy =
        TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = resources.getStringArray(R.array.array_food)[position]
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        initViewModel()
    }

    @SuppressLint("WrongConstant")
    private fun initUi() {
        getSelectGroceryList = intent.getStringArrayListExtra(KEY_SELECT_GROCERY_LIST)
        totalCalorie = intent.getDoubleExtra(KEY_TOTAL_CALORIE, 0.0)

        totalCalorie?.let {

        }

        val list = listOf(Food1Fragment(), Food2Fragment(), Food3Fragment())

        val pagerAdapter = FragmentPagerAdapter(list, this)

        with(binding) {
            viewPager.adapter = pagerAdapter
            viewPager.offscreenPageLimit = 3
            viewPager.isUserInputEnabled = false

            calorie = CalorieUtil().getKcal(totalCalorie)

            TabLayoutMediator(tabLayout, viewPager, tabConfigurationStrategy).attach()
        }
    }

    private fun initViewModel() {
        recommendViewModel.getGroceryList(getSelectGroceryList)
    }

    companion object {
        const val KEY_SELECT_GROCERY_LIST = "key_select_grocery_list"
        const val KEY_TOTAL_CALORIE = "key_total_calorie"
    }
}