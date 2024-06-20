package com.capstone.mageiras.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.mageiras.R
import com.capstone.mageiras.data.remote.response.RecipesItem
import com.capstone.mageiras.ui.recipeDetail.RecipeDetailActivity

class RecipeAdapter (private val listRecipe:  List<RecipesItem>) :
    RecyclerView.Adapter<RecipeAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipes_fragment_item_row, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listRecipe.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val recipe = listRecipe[position]

        holder.tvIngredientName.text = recipe.title
        Glide.with(holder.itemView.context)
            .load(recipe.imageUrl)
            .into(holder.ivpicture)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, RecipeDetailActivity::class.java)
            intent.putExtra(RecipeDetailActivity.EXTRA_RECIPES, recipe)
            startActivity(holder.itemView.context, intent, null)
        }
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvIngredientName: TextView = itemView.findViewById(R.id.tv_recipes_name)
        val ivpicture: ImageView = itemView.findViewById(R.id.iv_recipes_picture)
    }

}
