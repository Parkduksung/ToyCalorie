package com.example.toycalorie.ui.recommand

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableField
import com.example.toycalorie.base.BaseViewModel
import com.example.toycalorie.data.model.CalorieItem
import com.example.toycalorie.util.CalorieUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecommendViewModel @Inject constructor(app: Application) : BaseViewModel(app) {

    private val food1List = mutableListOf<CalorieItem>()
    private val food2List = mutableListOf<CalorieItem>()
    private val food3List = mutableListOf<CalorieItem>()

    val getCalorieObservableField = ObservableField<Double>()


    fun getGroceryList(list: ArrayList<String>?) {
        list?.let { viewStateChanged(RecommendViewState.GetGroceryList(list)) }
    }

    fun getFood1List(list: List<CalorieItem>) {
        food1List.clear()
        food1List.addAll(list)
        viewStateChanged(RecommendViewState.GetFood1List(food1List))
    }

    fun getFood2List(list: List<CalorieItem>) {
        food2List.clear()
        food2List.addAll(list)
        viewStateChanged(RecommendViewState.GetFood2List(food2List))
    }

    fun getFood3List(list: List<CalorieItem>) {
        food3List.clear()
        food3List.addAll(list)
        viewStateChanged(RecommendViewState.GetFood3List(food3List))
    }

    fun isAddCalorieItem(item: CalorieItem): Boolean {
        return CalorieUtil().getKcalRange(getCalorieObservableField.get())?.let { range ->
            return range.last > getSelectTotalCalorie() + (item.kcal?.toInt() ?: 0)
        } ?: false
    }


    fun getSelectTotalCalorie(): Int =
        food1List.sumOf { it.kcal?.toInt() ?: 0 } +
                food2List.sumOf { it.kcal?.toInt() ?: 0 } +
                food3List.sumOf { it.kcal?.toInt() ?: 0 }


    fun outOfRangeCalorie() {
        viewStateChanged(RecommendViewState.OutOfRangeCalorie)
    }
}
