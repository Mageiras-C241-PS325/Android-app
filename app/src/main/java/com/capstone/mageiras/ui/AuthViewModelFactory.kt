package com.capstone.mageiras.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.mageiras.data.repository.AuthRepository
import com.capstone.mageiras.di.Injection
import com.capstone.mageiras.ui.login.LoginViewModel
import com.capstone.mageiras.ui.register.RegisterViewModel

class AuthViewModelFactory(private val authRepository: AuthRepository): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(authRepository) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: AuthViewModelFactory? = null

        fun getInstance(): AuthViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: AuthViewModelFactory(Injection.provideRepository())
            }.also { instance = it }
    }
}