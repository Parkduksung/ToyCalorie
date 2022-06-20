package com.example.toycalorie.ui.calculate

import com.example.toycalorie.base.ViewState
import com.example.toycalorie.ui.calculate.CalculateViewModel.Human

sealed class CalculateViewState : ViewState {
    object HideInputTextFiled : CalculateViewState()
    data class CheckHeight(val isValid: Boolean, val message: String = "") : CalculateViewState()
    data class CheckWeight(val isValid: Boolean, val message: String = "") : CalculateViewState()
    data class CheckAge(val isValid: Boolean, val message: String = "") : CalculateViewState()
    data class RouteYield(val human: Human, val totalCalorie: Double) : CalculateViewState()
}