package com.example.toycalorie.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.toycalorie.R
import com.example.toycalorie.databinding.ItemGroceryBinding

class GroceryAdapter :
    RecyclerView.Adapter<GroceryViewHolder>() {

    private val groceryList = mutableListOf<GroceryItem>()

    private lateinit var onItemClick: (GroceryItem) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val binding = ItemGroceryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroceryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        holder.bind(groceryList[position], onItemClick)
    }

    override fun getItemCount(): Int =
        groceryList.size

    fun addAll(list: List<String>) {
        groceryList.clear()
        val toGroceryList = list.map { GroceryItem(it) }
        groceryList.addAll(toGroceryList)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (GroceryItem) -> Unit) {
        onItemClick = listener
    }

    fun toggleItem(item: GroceryItem, callback: () -> Unit) {
        if (groceryList.contains(item)) {
            val index = groceryList.indexOfFirst { it == item }
            groceryList[index].isSelect = !item.isSelect
            notifyItemChanged(index)
            callback.invoke()
        }
    }
}


class GroceryViewHolder(private val binding: ItemGroceryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: GroceryItem, onItemClick: (GroceryItem) -> Unit) {
        with(binding) {
            this.item = item

            if (item.isSelect) {
                tvGrocery.setBackgroundResource(R.drawable.bg_grocery_selected)
                tvGrocery.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.white
                    )
                )
            } else {
                tvGrocery.setBackgroundResource(R.drawable.bg_grocery_not_select)
                tvGrocery.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.black
                    )
                )
            }
            itemView.setOnClickListener {
                onItemClick.invoke(item)
            }
        }
    }
}

data class GroceryItem(
    val name: String,
    var isSelect: Boolean = false
)