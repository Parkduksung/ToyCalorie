package com.example.toycalorie.ui.recommand

import com.example.toycalorie.base.ViewState

sealed class RecommendViewState : ViewState {
    data class GetGroceryList(val list: ArrayList<String>) : RecommendViewState()
}
