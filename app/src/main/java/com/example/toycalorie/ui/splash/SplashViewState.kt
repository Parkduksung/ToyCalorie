package com.example.toycalorie.ui.splash

import com.example.toycalorie.base.ViewState

sealed class SplashViewState : ViewState {
    object RouteCalculate : SplashViewState()
    data class Error(val message: String) : SplashViewState()
}