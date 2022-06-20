package com.example.toycalorie.util

import com.example.toycalorie.ui.calculate.CalculateViewModel

class CalorieUtil {

    fun getCalorie(human: CalculateViewModel.Human): Double {
        return try {
            var totalScore = 65.0

            totalScore += if (human.gender == GENDER_MAIL) {
                (13.7 * human.weight) + (5.0 * human.height) - (6.8 * human.age)
            } else {
                (9.6 * human.weight) + (1.8 * human.height) - (4.7 * human.age)
            }

            totalScore *= if (human.exercise == 0) {
                1.375
            } else if (human.exercise == 1) {
                1.55
            } else if (human.exercise == 2) {
                1.725
            } else {
                1.2
            }
            return totalScore
        } catch (e: Exception) {
            0.0
        }
    }

    companion object {
        private const val GENDER_MAIL = 0
    }
}