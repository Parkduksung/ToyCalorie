package com.example.toycalorie.ui.grocery

import android.os.Bundle
import com.example.toycalorie.R
import com.example.toycalorie.base.BaseActivity
import com.example.toycalorie.databinding.ActivityGroceryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroceryActivity : BaseActivity<ActivityGroceryBinding>(R.layout.activity_grocery) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}