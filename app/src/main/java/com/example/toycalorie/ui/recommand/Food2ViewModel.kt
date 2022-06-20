package com.example.toycalorie.ui.recommand

import android.app.Application
import com.example.toycalorie.base.BaseViewModel
import com.example.toycalorie.base.ViewState
import com.example.toycalorie.data.model.CalorieItem
import com.example.toycalorie.data.repo.CalorieRepository
import com.example.toycalorie.ext.ioScope
import com.example.toycalorie.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class Food2ViewModel @Inject constructor(
    app: Application,
    private val calorieRepository: CalorieRepository
) : BaseViewModel(app) {

    fun getGroceryList(list: ArrayList<String>) {
        ioScope {
            when (val getResult1 = calorieRepository.getLocalCalorieList("국")) {

                is Result.Success -> {


                    ioScope {

                        when (val getResult2 = calorieRepository.getLocalCalorieList("면")) {
                            is Result.Success -> {


                                val toFoodList = mutableListOf<CalorieItem>()
                                val toList = list.toList()

                                getResult1.data.forEach {
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

                                getResult2.data.forEach {
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


                                viewStateChanged(Food2ViewState.GetFoodList(toFoodList))

                            }

                            is Result.Error -> {

                            }
                        }
                    }
                }

                is Result.Error -> {

                }
            }
        }
    }
}


sealed class Food2ViewState : ViewState {
    data class GetFoodList(val list: List<CalorieItem>) : Food2ViewState()
}