package com.capstone.mageiras.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.mageiras.R
import com.capstone.mageiras.data.dummy.DummyData

class ListIngredientsAdapter(private val listIngredients: ArrayList<DummyData.Ingredients>) :
    RecyclerView.Adapter<ListIngredientsAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingredients_list_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listIngredients.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val ingredients = listIngredients[position]

        holder.tvIngredientName.text = ingredients.name
        holder.tvIngredientsAmount.text = ingredients.amount
        Glide.with(holder.itemView.context)
            .load(ingredients.picture)
            .into(holder.imgPhoto)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.iv_ingredients_picture)
        val tvIngredientName: TextView = itemView.findViewById(R.id.tv_ingredients_name)
        val tvIngredientsAmount: TextView = itemView.findViewById(R.id.tv_ingredients_amount)
    }


}
