package com.capstone.mageiras.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.mageiras.data.repository.IngredientRepository
import com.capstone.mageiras.data.repository.PredictRepository
import com.capstone.mageiras.di.Injection
import com.capstone.mageiras.ui.result.ResultViewModel

class PredictViewModelFactory(private val predictRepository: PredictRepository, private val ingredientRepository: IngredientRepository): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            return ResultViewModel(predictRepository, ingredientRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: PredictViewModelFactory? = null

        fun getInstance(): PredictViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: PredictViewModelFactory(Injection.providePredictRepository(),Injection.provideIngredientRepository())
            }.also { instance = it }
    }
}