package com.example.toycalorie.ui.recommand

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.toycalorie.R
import com.example.toycalorie.base.BaseFragment
import com.example.toycalorie.data.model.CalorieItem
import com.example.toycalorie.databinding.FragmentFood3Binding
import com.example.toycalorie.ui.adapter.FoodAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Food3Fragment : BaseFragment<FragmentFood3Binding>(R.layout.fragment_food3) {

    private val recommendViewModel by activityViewModels<RecommendViewModel>()

    private val food3ViewModel by viewModels<Food3ViewModel>()

    private val foodAdapter = FoodAdapter()

    private val selectFood3Set = mutableSetOf<CalorieItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi() {
        with(binding) {
            rvFood3.adapter = foodAdapter
        }

        foodAdapter.setOnItemClickListener { item ->
            if (item.isSelected) {
                selectFood3Set.remove(item.calorieItem)
                foodAdapter.toggleItem(item) {
                    recommendViewModel.getFood3List(selectFood3Set.toList())
                }
            } else {
                if (recommendViewModel.isAddCalorieItem(item.calorieItem)) {
                    selectFood3Set.add(item.calorieItem)
                    foodAdapter.toggleItem(item) {
                        recommendViewModel.getFood3List(selectFood3Set.toList())
                    }
                } else {
                    recommendViewModel.outOfRangeCalorie()
                }
            }
        }
    }

    private fun initViewModel() {
        recommendViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? RecommendViewState)?.let {
                onChangedRecommendViewState(it)
            }
        }

        food3ViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? Food3ViewState)?.let {
                onChangedFood3ViewState(it)
            }
        }
    }

    private fun onChangedRecommendViewState(viewState: RecommendViewState) {
        when (viewState) {
            is RecommendViewState.GetGroceryList -> {
                food3ViewModel.getGroceryList(viewState.list)
            }
        }
    }

    private fun onChangedFood3ViewState(viewState: Food3ViewState) {
        when (viewState) {
            is Food3ViewState.GetFoodList -> {
                foodAdapter.addAll(viewState.list)
            }
        }
    }
}