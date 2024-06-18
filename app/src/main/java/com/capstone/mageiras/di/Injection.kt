package com.capstone.mageiras.di

import android.content.Context
import com.capstone.mageiras.data.remote.retrofit.ApiConfig
import com.capstone.mageiras.data.repository.AuthRepository
import com.capstone.mageiras.data.repository.PredictRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

object Injection {
    fun provideRepository(context: Context): AuthRepository {
        val auth: FirebaseAuth = Firebase.auth
        return AuthRepository.getInstance(auth)
    }

    fun providePredictRepository(): PredictRepository {
        val apiService = ApiConfig.getApiConfig()
        return PredictRepository.getInstance(apiService)
    }
}