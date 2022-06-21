package com.example.toycalorie.util

import com.example.toycalorie.ui.calculate.CalculateViewModel

class CalorieUtil {

    /**
     * 총 칼로리 계산.
     */
    fun getCalorie(human: CalculateViewModel.Human): Double {
        return try {
            var totalScore = 65.0

            /**
             * 성별 , 키, 몸무게, 연령
             */
            totalScore += if (human.gender == GENDER_MAIL) {
                (13.7 * human.weight) + (5.0 * human.height) - (6.8 * human.age)
            } else {
                (9.6 * human.weight) + (1.8 * human.height) - (4.7 * human.age)
            }

            /**
             * 운동 관련.
             * 0 - 가볍게 운동
             * 1 - 적당히 운동
             * 2 - 운동량 많음
             * else - 전혀 하지 않음
             */
            totalScore *= when (human.exercise) {
                0 -> {
                    1.375
                }
                1 -> {
                    1.55
                }
                2 -> {
                    1.725
                }
                else -> {
                    1.2
                }
            }
            return totalScore
        } catch (e: Exception) {
            0.0
        }
    }

    fun getKcal(totalCalorie: Double?): String {
        return totalCalorie?.let {

            val oneMealMin = ((it / 3) - (it / 3) / 10).toInt()
            val oneMealMax = ((it / 3) + (it / 3) / 10).toInt()

            "${oneMealMin}Kcal ~ ${oneMealMax}Kcal"

        } ?: ""
    }

    fun getKcalRange(totalCalorie: Double?): IntRange? {
        return totalCalorie?.let {
            val oneMealMin = ((it / 3) - (it / 3) / 10).toInt()
            val oneMealMax = ((it / 3) + (it / 3) / 10).toInt()
            IntRange(oneMealMin, oneMealMax)
        }
    }



    companion object {
        private const val GENDER_MAIL = 0
    }
}