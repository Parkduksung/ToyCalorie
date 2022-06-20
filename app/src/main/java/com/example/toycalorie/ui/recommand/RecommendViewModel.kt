package com.example.toycalorie.ui.recommand

import android.app.Application
import com.example.toycalorie.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecommendViewModel @Inject constructor(app: Application) : BaseViewModel(app) {

    fun getGroceryList(list: ArrayList<String>?) {
        list?.let { viewStateChanged(RecommendViewState.GetGroceryList(list)) }
    }

}