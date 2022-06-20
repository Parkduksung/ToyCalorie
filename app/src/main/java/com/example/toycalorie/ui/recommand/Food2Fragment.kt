package com.example.toycalorie.ui.recommand

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.toycalorie.R
import com.example.toycalorie.base.BaseFragment
import com.example.toycalorie.data.model.CalorieItem
import com.example.toycalorie.databinding.FragmentFood2Binding
import com.example.toycalorie.ui.adapter.FoodAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Food2Fragment : BaseFragment<FragmentFood2Binding>(R.layout.fragment_food2) {

    private val recommendViewModel by activityViewModels<RecommendViewModel>()

    private val food2ViewModel by viewModels<Food2ViewModel>()

    private val foodAdapter = FoodAdapter()

    private val selectFood2Set = mutableSetOf<CalorieItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi() {
        with(binding) {
            rvFood2.adapter = foodAdapter
        }

        foodAdapter.setOnItemClickListener { item ->
            if (item.isSelected) {
                selectFood2Set.remove(item.calorieItem)
            } else {
                selectFood2Set.add(item.calorieItem)
            }
            foodAdapter.toggleItem(item) {
//                binding.tvSelectGrocery.text = selectGrocerySet.joinToString(", ")
            }
        }
    }

    private fun initViewModel() {
        recommendViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? RecommendViewState)?.let {
                onChangedRecommendViewState(it)
            }
        }

        food2ViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? Food2ViewState)?.let {
                onChangedFood2ViewState(it)
            }
        }
    }

    private fun onChangedRecommendViewState(viewState: RecommendViewState) {
        when (viewState) {
            is RecommendViewState.GetGroceryList -> {
                food2ViewModel.getGroceryList(viewState.list)
            }
        }
    }

    private fun onChangedFood2ViewState(viewState: Food2ViewState) {
        when (viewState) {
            is Food2ViewState.GetFoodList -> {
                foodAdapter.addAll(viewState.list)
            }
        }
    }
}