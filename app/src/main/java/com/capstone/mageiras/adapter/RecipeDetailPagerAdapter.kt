package com.capstone.mageiras.adapter

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.mageiras.data.dummy.DummyData
import com.capstone.mageiras.data.remote.response.IngredientsItem
import com.capstone.mageiras.data.remote.response.RecipesItem
import com.capstone.mageiras.ui.recipeDetail.DetailIngredientsFragment
import com.capstone.mageiras.ui.recipeDetail.DetailStepsFragment
import java.time.LocalDateTime

//TODO CHANGE TO REAL DATA
class RecipeDetailPagerAdapter(
    private val activity: AppCompatActivity,
    private val recipes: RecipesItem
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                val ingredients: ArrayList<IngredientsItem> =
                    recipes.ingredients.split(";").map { ingredient ->
                        if (ingredient == "") {
                            return@map null
                        }
                        IngredientsItem(
                            amount = 1,
                            lastUpdate = LocalDateTime.now().toString(),
                            name = ingredient,
                            "1"
                        )
                    } as ArrayList<IngredientsItem>
                Log.d("RecipeDetailPagerAdapter", "createFragment: $ingredients")
                fragment = DetailIngredientsFragment.newInstance(ingredients)
            }

            1 -> {
                val steps: ArrayList<String> = recipes.directions.split(";").map {
                    step ->
                    if (step == "") {
                        return@map null
                    }
                    step
                } as ArrayList<String>
                fragment = DetailStepsFragment.newInstance(steps)
            }
        }
        return fragment as Fragment
    }

}