package com.example.toycalorie.ui.calculate

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.example.toycalorie.R
import com.example.toycalorie.base.BaseActivity
import com.example.toycalorie.databinding.ActivityCalculateBinding
import com.example.toycalorie.ext.hideError
import com.example.toycalorie.ext.hideKeyboard
import com.example.toycalorie.ext.showError
import com.example.toycalorie.ui.calculate.CalculateViewModel.Companion.INIT_POSITION
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalculateActivity : BaseActivity<ActivityCalculateBinding>(R.layout.activity_calculate) {

    private val calculateViewModel by viewModels<CalculateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi() {

        calculateViewModel.initHumanData()

        val itemsGender = resources.getStringArray(R.array.array_gender)
        val itemsExercise = resources.getStringArray(R.array.array_exercise)

        with(binding) {
            filledExposedDropdownGender.setAdapter(
                ArrayAdapter(this@CalculateActivity, R.layout.list_item, itemsGender)
            )
            filledExposedDropdownExercise.setAdapter(
                ArrayAdapter(this@CalculateActivity, R.layout.list_item, itemsExercise)
            )
            filledExposedDropdownGender.setText(itemsGender[INIT_POSITION], false)
            filledExposedDropdownExercise.setText(itemsExercise[INIT_POSITION], false)

            filledTextFieldHeight.hideError()
            filledTextFieldWeight.hideError()
            filledTextFieldAge.hideError()

            age.text?.clear()
            weight.text?.clear()
            height.text?.clear()
        }
    }

    private fun initViewModel() {
        binding.viewModel = calculateViewModel

        calculateViewModel.viewStateLiveData.observe(this) { viewState ->
            (viewState as? CalculateViewState)?.let { onChangedCalculateViewState(viewState) }
        }
    }

    private fun onChangedCalculateViewState(viewState: CalculateViewState) {
        when (viewState) {
            is CalculateViewState.HideInputTextFiled -> {
                binding.root.hideKeyboard()
            }

            is CalculateViewState.CheckHeight -> {
                if (viewState.isValid) {
                    binding.filledTextFieldHeight.hideError()
                } else {
                    binding.filledTextFieldHeight.showError(viewState.message)
                }
            }

            is CalculateViewState.CheckWeight -> {
                if (viewState.isValid) {
                    binding.filledTextFieldWeight.hideError()
                } else {
                    binding.filledTextFieldWeight.showError(viewState.message)
                }
            }

            is CalculateViewState.CheckAge -> {
                if (viewState.isValid) {
                    binding.filledTextFieldAge.hideError()
                } else {
                    binding.filledTextFieldAge.showError(viewState.message)
                }
            }

            is CalculateViewState.RouteYield -> {
                initUi()
            }
        }
    }
}