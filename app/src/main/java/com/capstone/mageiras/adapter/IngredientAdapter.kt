package com.capstone.mageiras.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.mageiras.R
import com.capstone.mageiras.data.remote.response.IngredientsItem

class IngredientAdapter(private val listIngredients:  List<IngredientsItem>) :
    RecyclerView.Adapter<IngredientAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingredients_list_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listIngredients.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val ingredients = listIngredients[position]

        holder.tvIngredientName.text = ingredients.name
        holder.tvIngredientsAmount.text = ingredients.amount.toString()
    }
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvIngredientName: TextView = itemView.findViewById(R.id.tv_ingredients_name)
        val tvIngredientsAmount: TextView = itemView.findViewById(R.id.tv_ingredients_amount)
    }

}
