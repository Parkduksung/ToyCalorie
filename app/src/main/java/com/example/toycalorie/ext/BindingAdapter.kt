package com.example.toycalorie.ext

import android.widget.AutoCompleteTextView
import androidx.databinding.BindingAdapter
import com.example.toycalorie.constant.ItemType

@BindingAdapter("itemType", "onItemClickListener")
fun AutoCompleteTextView.onItemClickListener(
    type: ItemType,
    f: Function2<ItemType, Int, Unit>?
) {
    if (f == null) onItemClickListener = null
    else setOnItemClickListener { _, view, position, _ ->
        view.hideKeyboard()
        f(type, position)
    }
}

