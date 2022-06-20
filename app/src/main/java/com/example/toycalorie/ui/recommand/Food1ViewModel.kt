package com.example.toycalorie.ui.recommand

import android.app.Application
import android.util.Log
import com.example.toycalorie.base.BaseViewModel
import com.example.toycalorie.base.ViewState
import com.example.toycalorie.data.model.CalorieItem
import com.example.toycalorie.data.repo.CalorieRepository
import com.example.toycalorie.ext.ioScope
import com.example.toycalorie.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class Food1ViewModel @Inject constructor(
    app: Application,
    private val calorieRepository: CalorieRepository
) : BaseViewModel(app) {

    fun getGroceryList(list: ArrayList<String>) {

        ioScope {
            when (val result = calorieRepository.getLocalCalorieList("밥")) {

                is Result.Success -> {
                    val toFoodList = mutableListOf<CalorieItem>()
                    val toList = list.toList()

                    result.data.forEach {
                        var count = 0

                        if (it.material1 == "") {
                            count += 1
                        }

                        if (it.material2 == "") {
                            count += 1
                        }

                        if (it.material3 == "") {
                            count += 1
                        }

                        if (toList.contains(it.material1)) {
                            count += 1
                        }
                        if (toList.contains(it.material2)) {
                            count += 1
                        }
                        if (toList.contains(it.material3)) {
                            count += 1
                        }

                        if (count >= 2) {
                            toFoodList.add(it.toCalorieItem())
                        }
                    }
                    viewStateChanged(Food1ViewState.GetFoodList(toFoodList))
                }

                is Result.Error -> {

                }
            }
        }

    }

}


sealed class Food1ViewState : ViewState {
    data class GetFoodList(val list: List<CalorieItem>) : Food1ViewState()
}