package com.example.toycalorie.ui.recommand

import com.example.toycalorie.base.ViewState
import com.example.toycalorie.data.model.CalorieItem

sealed class RecommendViewState : ViewState {
    data class GetGroceryList(val list: ArrayList<String>) : RecommendViewState()
    data class GetFood1List(val list: List<CalorieItem>) : RecommendViewState()
    data class GetFood2List(val list: List<CalorieItem>) : RecommendViewState()
    data class GetFood3List(val list: List<CalorieItem>) : RecommendViewState()
    object OutOfRangeCalorie : RecommendViewState()
}
