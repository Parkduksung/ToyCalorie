package com.example.toycalorie.ui.grocery

import android.app.Application
import com.example.toycalorie.base.BaseViewModel
import com.example.toycalorie.data.repo.CalorieRepository
import com.example.toycalorie.ext.ioScope
import com.example.toycalorie.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroceryViewModel @Inject constructor(
    app: Application,
    private val calorieRepository: CalorieRepository
) : BaseViewModel(app) {

    private val groceryList = mutableSetOf<String>()

    init {
        renewGroceryList()
    }

    fun renewGroceryList() {
        ioScope {

            when (val result = calorieRepository.getLocalCalorieList()) {

                is Result.Success -> {

                    val material1List = result.data.map { it.material1 ?: "" }.filter { it != "" }
                    val material2List = result.data.map { it.material2 ?: "" }.filter { it != "" }
                    val material3List = result.data.map { it.material3 ?: "" }.filter { it != "" }

                    groceryList.addAll(material1List)
                    groceryList.addAll(material2List)
                    groceryList.addAll(material3List)

                    viewStateChanged(GroceryViewState.GetGroceryList(groceryList.toList()))
                }

                is Result.Error -> {

                }

            }
        }
    }
}