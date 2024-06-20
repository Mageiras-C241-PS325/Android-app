package com.capstone.mageiras.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.mageiras.data.repository.IngredientRepository
import com.capstone.mageiras.data.repository.PredictRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ResultViewModel (
    private val predictRepository: PredictRepository,
    private val ingredientRepository: IngredientRepository
) : ViewModel() {

    fun predictImage(image: MultipartBody.Part) = predictRepository.predictImage(image)

//    fun addIngredient(name: String, amount: Int) = ingredientRepository.addIngredient(name, amount)

    fun addManyIngredients(ingredients: RequestBody) = ingredientRepository.addManyIngredients(ingredients)



}