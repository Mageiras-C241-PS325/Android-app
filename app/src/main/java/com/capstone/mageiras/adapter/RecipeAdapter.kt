package com.capstone.mageiras.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.mageiras.R
import com.capstone.mageiras.adapter.ListIngredientsAdapter.ListViewHolder
import com.capstone.mageiras.data.dummy.DummyData
import com.capstone.mageiras.data.remote.response.IngredientsItem
import com.capstone.mageiras.databinding.IngredientsListItemBinding

class RecipeAdapter(private val listIngredients:  List<IngredientsItem>) :
    RecyclerView.Adapter<RecipeAdapter.ListViewHolder>() {

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
