package com.capstone.mageiras.di

import com.capstone.mageiras.data.remote.getIdToken
import com.capstone.mageiras.data.remote.retrofit.ApiConfig
import com.capstone.mageiras.data.repository.AuthRepository
import com.capstone.mageiras.data.repository.IngredientRepository
import com.capstone.mageiras.data.repository.PredictRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

object Injection {
    fun provideRepository(): AuthRepository {
        val auth: FirebaseAuth = Firebase.auth
        val idToken = getIdToken()
        val apiService = ApiConfig.getApiService(idToken)
        return AuthRepository.getInstance(auth,apiService)
    }

    fun providePredictRepository(): PredictRepository {
        val apiService = ApiConfig.getMLApiConfig()
        return PredictRepository.getInstance(apiService)
    }

    fun provideIngredientRepository(): IngredientRepository {
        val token = getIdToken()
        val apiService = ApiConfig.getApiService(token)
        return IngredientRepository.getInstance(apiService)
    }

}