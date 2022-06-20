package com.example.toycalorie.ui.recommand

import android.os.Bundle
import com.example.toycalorie.R
import com.example.toycalorie.base.BaseActivity
import com.example.toycalorie.databinding.ActivityRecommendBinding
import com.example.toycalorie.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendActivity : BaseActivity<ActivityRecommendBinding>(R.layout.activity_recommend) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}