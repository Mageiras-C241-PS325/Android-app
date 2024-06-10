package com.capstone.mageiras.ui.login

import androidx.lifecycle.ViewModel
import com.capstone.mageiras.data.repository.AuthRepository

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    fun signIn(email: String, password: String) =
        repository.signIn(email, password)

}