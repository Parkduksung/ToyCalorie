package com.example.toycalorie.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.toycalorie.R
import com.example.toycalorie.data.model.CalorieItem
import com.example.toycalorie.databinding.ItemFoodBinding

class FoodAdapter : RecyclerView.Adapter<FoodViewHolder>() {

    private val foodList = mutableListOf<FoodItem>()

    private lateinit var onItemClick: (FoodItem) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodList[position], onItemClick)
    }

    override fun getItemCount(): Int =
        foodList.size

    fun addAll(list: List<CalorieItem>) {
        foodList.clear()
        val toFoodItem = list.map { FoodItem(it, false) }
        foodList.addAll(toFoodItem)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (FoodItem) -> Unit) {
        onItemClick = listener
    }

    fun toggleItem(item: FoodItem, callback: () -> Unit) {
        if (foodList.contains(item)) {
            val index = foodList.indexOfFirst { it == item }
            foodList[index].isSelected = !item.isSelected
            notifyItemChanged(index)
            callback.invoke()
        }
    }
}

data class FoodItem(
    val calorieItem: CalorieItem,
    var isSelected: Boolean = false
)

class FoodViewHolder(private val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bind(item: FoodItem, onItemClick: (FoodItem) -> Unit) {
        with(binding) {
            this.item = item.calorieItem.name

            if (item.isSelected) {
                tvName.setBackgroundResource(R.drawable.bg_grocery_selected)
                tvName.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white
                    )
                )
            } else {
                tvName.setBackgroundResource(R.drawable.bg_grocery_not_select)
                tvName.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.black
                    )
                )
            }
        }

        itemView.setOnClickListener {
            onItemClick.invoke(item)
        }

    }

}