package com.example.toycalorie.ui.grocery

import com.example.toycalorie.base.ViewState

sealed class GroceryViewState : ViewState {
    data class GetGroceryList(val list: List<String>) : GroceryViewState()
    data class RouteRecommend(val list: ArrayList<String>) : GroceryViewState()
    object InvalidYield : GroceryViewState()
}