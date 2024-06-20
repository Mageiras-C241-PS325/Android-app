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
import com.capstone.mageiras.data.dummy.DummyData
import com.capstone.mageiras.databinding.RecipesCardBinding
import com.capstone.mageiras.ui.recipeDetail.RecipeDetailActivity

class ListRecipesAdapter(private val listRecipes: ArrayList<DummyData.Recipes>) : RecyclerView.Adapter<ListRecipesAdapter.ListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recipes_card, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listRecipes.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val recipes = listRecipes[position]

        holder.tvName.text = recipes.title
//        holder.tvCookingTime.text = recipes.cookingTime
        Glide.with(holder.itemView.context)
            .load(recipes.picture)
            .into(holder.imgPhoto)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, RecipeDetailActivity::class.java)
            intent.putExtra(RecipeDetailActivity.EXTRA_RECIPES, recipes)
            startActivity(holder.itemView.context, intent, null)
        }

    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.iv_recipes_picture)
        val tvName: TextView = itemView.findViewById(R.id.tv_recipes_name)
//        val tvCookingTime: TextView = itemView.findViewById(R.id.tv_recipes_cooking_time)
    }
}