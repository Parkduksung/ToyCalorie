package com.example.toycalorie.ui.recommand

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import com.example.toycalorie.R
import com.example.toycalorie.base.BaseActivity
import com.example.toycalorie.data.model.CalorieItem
import com.example.toycalorie.databinding.ActivityRecommendBinding
import com.example.toycalorie.ext.showToast
import com.example.toycalorie.ui.adapter.FragmentPagerAdapter
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


        val list = listOf(Food1Fragment(), Food2Fragment(), Food3Fragment())

        val pagerAdapter = FragmentPagerAdapter(list, this)

        with(binding) {
            viewPager.adapter = pagerAdapter
            viewPager.offscreenPageLimit = 3
            viewPager.isUserInputEnabled = false


            calorie = CalorieUtil().getKcal(totalCalorie)

            TabLayoutMediator(tabLayout, viewPager, tabConfigurationStrategy).attach()

            food1 = "밥 : 선택없음"
            food2 = "국/면 : 선택없음"
            food3 = "반찬 : 선택없음"
            selectCalorie = "0Kcal"
        }
    }

    private fun initViewModel() {
        recommendViewModel.getGroceryList(getSelectGroceryList)
        recommendViewModel.getCalorieObservableField.set(totalCalorie)

        recommendViewModel.viewStateLiveData.observe(this) { viewState ->
            (viewState as? RecommendViewState)?.let { onChangedRecommendViewState(it) }
        }
    }


    private fun onChangedRecommendViewState(viewState: RecommendViewState) {
        when (viewState) {
            is RecommendViewState.GetFood1List -> {
                if (viewState.list.isNotEmpty()) {
                    binding.food1 =
                        "밥 : ${viewState.list.map { it.name }.joinToString(", ")}"
                } else {
                    binding.food1 = "밥 : 선택없음"
                }
                setSelectCalorie()
            }

            is RecommendViewState.GetFood2List -> {
                if (viewState.list.isNotEmpty()) {
                    binding.food2 =
                        "국/면 : ${viewState.list.map { it.name }.joinToString(", ")}"
                } else {
                    binding.food2 = "국/면 : 선택없음"
                }
                setSelectCalorie()
            }

            is RecommendViewState.GetFood3List -> {
                if (viewState.list.isNotEmpty()) {
                    binding.food3 =
                        "반찬 : ${viewState.list.map { it.name }.joinToString(", ")}"
                } else {
                    binding.food3 = "반찬 : 선택없음"
                }
                setSelectCalorie()
            }

            is RecommendViewState.OutOfRangeCalorie -> {
                showToast(message = "한끼에 먹을수 있는 칼로리를 초과하였습니다.")
            }
        }
    }

    private fun setSelectCalorie() {
        binding.selectCalorie = "${recommendViewModel.getSelectTotalCalorie()}Kcal"
    }

    companion object {
        const val KEY_SELECT_GROCERY_LIST = "key_select_grocery_list"
        const val KEY_TOTAL_CALORIE = "key_total_calorie"
    }
}