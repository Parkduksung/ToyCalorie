package com.example.toycalorie.ui.splash

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.toycalorie.R
import com.example.toycalorie.base.BaseActivity
import com.example.toycalorie.databinding.ActivitySplashBinding
import com.example.toycalorie.ext.showToast
import com.example.toycalorie.ui.calculate.CalculateActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val splashViewModel by viewModels<SplashViewModel>()

    /**
     * 화면전환을 해도 되는지의 여부를 결정짓는 변수.
     */
    private var isRoute = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        initViewModel()
    }

    private fun initUi() {

        /**
         * 애니메이션 관련 리스너
         */
        binding.lottieView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            /**
             * 애니메이션 cycle 이 끝났을때 나타내는 콜백 함수.
             */
            override fun onAnimationEnd(animation: Animator) {

                /**
                 * isRoute Boolean 값에 따른 화면 상태를 나타냄
                 */
                if (isRoute) {
                    startActivity(Intent(this@SplashActivity, CalculateActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    })
                } else {
                    /**
                     * 데이터를 가져올때 시간이 애니메이션 끝났을때보다 길 경우 로딩되고 있다는 문구를 나타내고 다시 애니메이션 시작하게함.
                     */
                    with(binding) {
                        tvLoading.isVisible = true
                        tvLoading.startAnimation(
                            AnimationUtils.loadAnimation(
                                applicationContext,
                                R.anim.anim_brick
                            )
                        )
                        lottieView.playAnimation()
                    }
                }
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
    }

    /**
     * 뷰모델 초기화
     */
    private fun initViewModel() {
        splashViewModel.viewStateLiveData.observe(this) { viewState ->
            (viewState as? SplashViewState)?.let {
                onChangedSplashViewState(
                    viewState
                )
            }
        }
    }

    /**
     * 상태에 따른 화면변화를 나타냄
     */
    private fun onChangedSplashViewState(viewState: SplashViewState) {
        when (viewState) {

            /**
             * CalculateActivity 로 화면 전환
             */
            is SplashViewState.RouteCalculate -> {
                isRoute = true
            }

            /**
             * 스플래시 화면전환 실패시 에러 메세지 보여주고 앱종료.
             */
            is SplashViewState.Error -> {
                showToast(message = viewState.message)
                exitProcess(0)
            }
        }
    }
}