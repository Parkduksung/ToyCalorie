package com.example.toycalorie.ui.splash

import android.app.Application
import com.example.toycalorie.base.BaseViewModel
import com.example.toycalorie.data.repo.CalorieRepository
import com.example.toycalorie.ext.ioScope
import com.example.toycalorie.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    app: Application,
    private val calorieRepository: CalorieRepository
) : BaseViewModel(app) {

    init {
        checkSaveCalories()
    }

    /**
     * 현재 저장되어있는 음식 데이터가 있는지 체크
     * 있을경우, 앞의 변수 isRoute 를 true 로 바꾸게 해줌.
     * 없을경우 loadCalorie() 호출
     * 체크하는 로직이 실패할 경우 에러메세지 노출.
     */
    private fun checkSaveCalories() {
        ioScope {
            when (val result = calorieRepository.getLocalCalorieList()) {
                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        loadCalories()
                    } else {
                        viewStateChanged(SplashViewState.RouteCalculate)
                    }
                }
                is Result.Error -> {
                    viewStateChanged(SplashViewState.Error("저장된 음식 데이터를 가지고 올 수 없습니다. 다시 시도해 주세요."))
                }
            }
        }
    }

    private fun loadCalories() {
        ioScope {

            /**
             * 엑셀파일 데이터를 읽어오는 로직 호출.
             */
            when (val result = calorieRepository.getRemoteCalorieList()) {

                /**
                 * 엑셀파일 데이터 가져오는 결과 성공
                 */
                is Result.Success -> {

                    /**
                     * 저장할 수 있는 entity 형식으로 변경.
                     */
                    val toCalorieEntityList = result.data.map { it.toCalorieEntity() }


                    /**
                     * 음식데이터가 로컬 DB 에 모두 저장되었는지 확인하는 로직.
                     */
                    ioScope {
                        if (calorieRepository.registerCalorieEntityList(toCalorieEntityList)) {

                            /**
                             * 음식데이터가 로컬 DB 에 모두 저장 성공
                             */
                            viewStateChanged(SplashViewState.RouteCalculate)

                        } else {

                            /**
                             * 음식데이터가 로컬 DB 에 모두 저장 실패
                             * 실패 에러메세지 호출.
                             */
                            viewStateChanged(SplashViewState.Error("음식 데이터 저장을 실패하였습니다."))

                        }
                    }

                }

                /**
                 * 엑셀파일 데이터 가져오는 결과 실패
                 * 실패 에러메세지 호출.
                 */
                is Result.Error -> {
                    viewStateChanged(SplashViewState.Error("음식 데이터를 가지고 올 수 없습니다. 다시 시도해 주세요."))
                }
            }

        }
    }

}