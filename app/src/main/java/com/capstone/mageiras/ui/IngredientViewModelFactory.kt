package com.capstone.mageiras.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.mageiras.data.repository.AuthRepository
import com.capstone.mageiras.data.repository.IngredientRepository
import com.capstone.mageiras.di.Injection
import com.capstone.mageiras.ui.home.HomeViewModel
import com.capstone.mageiras.ui.login.LoginViewModel
import com.capstone.mageiras.ui.recipe.RecipeViewModel
import com.capstone.mageiras.ui.register.RegisterViewModel

class IngredientViewModelFactory(private val ingredientRepository: IngredientRepository): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(ingredientRepository) as T
        }
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            return RecipeViewModel(ingredientRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: IngredientViewModelFactory? = null

        fun getInstance(): IngredientViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: IngredientViewModelFactory(Injection.provideIngredientRepository())
            }.also { instance = it }
    }
}