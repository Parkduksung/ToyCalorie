package com.example.toycalorie.ui.grocery

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.toycalorie.R
import com.example.toycalorie.base.BaseActivity
import com.example.toycalorie.databinding.ActivityGroceryBinding
import com.example.toycalorie.ext.hideKeyboard
import com.example.toycalorie.ext.showToast
import com.example.toycalorie.ext.textChanges
import com.example.toycalorie.ui.adapter.GroceryAdapter
import com.example.toycalorie.ui.calculate.CalculateViewModel
import com.example.toycalorie.ui.recommand.RecommendActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GroceryActivity : BaseActivity<ActivityGroceryBinding>(R.layout.activity_grocery) {

    private val groceryViewModel by viewModels<GroceryViewModel>()

    private var getHuman: CalculateViewModel.Human? = null
    private var totalCalorie: Double? = null

    private val selectGrocerySet = mutableSetOf<String>()

    private val groceryAdapter = GroceryAdapter()

    private var isStopAndDestroy = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        initViewModel()
    }

    @SuppressLint("WrongConstant")
    private fun initUi() {
        getHuman = intent.getParcelableExtra(KEY_HUMAN)
        totalCalorie = intent.getDoubleExtra(KEY_TOTAL_CALORIE, 0.0)

        with(binding) {
            rvGrocery.adapter = groceryAdapter
            tvYield.setOnClickListener {
                groceryViewModel.yield(selectGrocerySet.toList())
            }
        }

        groceryAdapter.setOnItemClickListener { item ->
            if (item.isSelect) {
                selectGrocerySet.remove(item.name)
            } else {
                selectGrocerySet.add(item.name)
            }
            groceryAdapter.toggleItem(item) {
                binding.tvSelectGrocery.text = selectGrocerySet.joinToString(", ")
            }
        }
    }

    /**
     * 뷰모델 초기화
     */
    private fun initViewModel() {
        groceryViewModel.viewStateLiveData.observe(this) { viewState ->
            (viewState as? GroceryViewState)?.let {
                onChangedGroceryViewState(
                    viewState
                )
            }
        }
    }

    /**
     * 상태에 따른 화면변화를 나타냄
     */
    private fun onChangedGroceryViewState(viewState: GroceryViewState) {
        when (viewState) {

            /**
             * 전체 식료품 리스트 가져옴.
             */
            is GroceryViewState.GetGroceryList -> {
                groceryAdapter.addAll(viewState.list)
            }

            is GroceryViewState.RouteRecommend -> {
                val intent = Intent(this, RecommendActivity::class.java).apply {
                    putStringArrayListExtra(
                        RecommendActivity.KEY_SELECT_GROCERY_LIST,
                        viewState.list
                    )
                    putExtra(RecommendActivity.KEY_TOTAL_CALORIE, totalCalorie)
                }
                startActivity(intent)
            }

            is GroceryViewState.InvalidYield -> {
                showToast(message = "3개 이상 식료품을 선택하세요.")
            }
        }
    }

    override fun onStop() {
        isStopAndDestroy = true
        super.onStop()
    }

    override fun onDestroy() {
        isStopAndDestroy = true
        super.onDestroy()
    }


    companion object {
        const val KEY_HUMAN = "key_human"
        const val KEY_TOTAL_CALORIE = "key_total_calorie"
    }
}