package com.example.toycalorie.ui.calculate

import android.app.Application
import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import com.example.toycalorie.base.BaseViewModel
import com.example.toycalorie.constant.ItemType
import com.example.toycalorie.ext.ioScope
import com.example.toycalorie.util.CalorieUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@HiltViewModel
class CalculateViewModel @Inject constructor(app: Application) : BaseViewModel(app) {

    val inputItemPositionAdapter: Function2<ItemType, Int, Unit> = this::setTypePosition

    val inputHeightLiveData = MutableLiveData<String>()
    val inputWeightLiveData = MutableLiveData<String>()
    val inputAgeLiveData = MutableLiveData<String>()

    private val inputExercisePositionLiveData = MutableLiveData(INIT_POSITION)
    private val inputGenderPositionLiveData = MutableLiveData(INIT_POSITION)


    fun hideInputTextField() {
        viewStateChanged(CalculateViewState.HideInputTextFiled)
    }

    fun yield() {
        hideInputTextField()
        ioScope {
            val checkHeight = async { checkInputHeight() }
            val checkWeight = async { checkInputWeight() }
            val checkAge = async { checkInputAge() }

            checkHuman(checkHeight.await(), checkWeight.await(), checkAge.await())?.let { human ->
                val getCalorie = CalorieUtil().getCalorie(human)
                viewStateChanged(CalculateViewState.RouteYield(human, getCalorie))
            }
        }
    }

    private fun setTypePosition(type: ItemType, position: Int) {
        when (type) {
            ItemType.EXERCISE -> {
                inputExercisePositionLiveData.value = position
            }
            ItemType.GENDER -> {
                inputGenderPositionLiveData.value = position
            }
        }
    }

    private fun checkHuman(checkHeight: Boolean, checkWeight: Boolean, checkAge: Boolean): Human? {
        return if (checkHeight && checkWeight && checkAge) {
            Human(
                inputHeightLiveData.value!!.toInt(),
                inputWeightLiveData.value!!.toInt(),
                inputAgeLiveData.value!!.toInt(),
                inputExercisePositionLiveData.value!!,
                inputGenderPositionLiveData.value!!,
            )
        } else {
            null
        }
    }

    private fun checkInputHeight(): Boolean {
        return if (inputHeightLiveData.value.isNullOrEmpty()) {
            viewStateChanged(CalculateViewState.CheckHeight(isValid = false, "키를 입력해주세요."))
            false
        } else {
            viewStateChanged(CalculateViewState.CheckHeight(isValid = true))
            true
        }
    }

    private fun checkInputWeight(): Boolean {
        return if (inputWeightLiveData.value.isNullOrEmpty()) {
            viewStateChanged(CalculateViewState.CheckWeight(isValid = false, "몸무게를 입력해주세요."))
            false
        } else {
            viewStateChanged(CalculateViewState.CheckHeight(isValid = true))
            true
        }
    }

    private fun checkInputAge(): Boolean {
        return if (inputAgeLiveData.value.isNullOrEmpty()) {
            viewStateChanged(CalculateViewState.CheckAge(isValid = false, "연령을 입력해주세요."))
            false
        } else {
            viewStateChanged(CalculateViewState.CheckHeight(isValid = true))
            true
        }
    }

    fun initHumanData() {
        inputHeightLiveData.value = ""
        inputWeightLiveData.value = ""
        inputAgeLiveData.value = ""
        inputExercisePositionLiveData.value = INIT_POSITION
        inputGenderPositionLiveData.value = INIT_POSITION
    }

    companion object {
        const val INIT_POSITION = 0
    }

    @Parcelize
    data class Human(
        val height: Int,
        val weight: Int,
        val age: Int,
        val exercise: Int,
        val gender: Int
    ) : Parcelable
}