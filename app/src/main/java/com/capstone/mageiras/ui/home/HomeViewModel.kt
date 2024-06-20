package com.capstone.mageiras.ui.home

import androidx.lifecycle.ViewModel
import com.capstone.mageiras.data.repository.IngredientRepository
import com.capstone.mageiras.data.repository.PredictRepository
import okhttp3.MultipartBody

class HomeViewModel (
    private val ingredientRepository: IngredientRepository
) : ViewModel() {

    fun getIngredients() = ingredientRepository.getIngredients()
    fun getRecipes() = ingredientRepository.getRecommendRecipes()
}