package com.capstone.mageiras.ui.recipe

import androidx.lifecycle.ViewModel
import com.capstone.mageiras.data.repository.IngredientRepository

class RecipeViewModel (
    private val ingredientRepository: IngredientRepository
) : ViewModel() {

    fun getRecipes() = ingredientRepository.getRecommendRecipes()

}