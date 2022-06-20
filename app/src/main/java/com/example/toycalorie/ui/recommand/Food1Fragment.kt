package com.example.toycalorie.ui.recommand

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.toycalorie.R
import com.example.toycalorie.base.BaseFragment
import com.example.toycalorie.data.model.CalorieItem
import com.example.toycalorie.databinding.FragmentFood1Binding
import com.example.toycalorie.ui.adapter.FoodAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Food1Fragment : BaseFragment<FragmentFood1Binding>(R.layout.fragment_food1) {

    private val recommendViewModel by activityViewModels<RecommendViewModel>()

    private val food1ViewModel by viewModels<Food1ViewModel>()

    private val foodAdapter = FoodAdapter()

    private val selectFood1Set = mutableSetOf<CalorieItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi() {
        with(binding) {
            rvFood1.adapter = foodAdapter
        }

        foodAdapter.setOnItemClickListener { item ->
            if (item.isSelected) {
                selectFood1Set.remove(item.calorieItem)
            } else {
                selectFood1Set.add(item.calorieItem)
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

        food1ViewModel.viewStateLiveData.observe(viewLifecycleOwner) { viewState ->
            (viewState as? Food1ViewState)?.let {
                onChangedFood1ViewState(it)
            }
        }
    }

    private fun onChangedRecommendViewState(viewState: RecommendViewState) {
        when (viewState) {
            is RecommendViewState.GetGroceryList -> {
                food1ViewModel.getGroceryList(viewState.list)
            }
        }
    }

    private fun onChangedFood1ViewState(viewState: Food1ViewState) {
        when (viewState) {
            is Food1ViewState.GetFoodList -> {
                foodAdapter.addAll(viewState.list)
            }
        }
    }
}