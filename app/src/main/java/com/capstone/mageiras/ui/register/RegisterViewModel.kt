package com.capstone.mageiras.ui.register

import androidx.lifecycle.ViewModel
import com.capstone.mageiras.data.repository.AuthRepository

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {

    fun createAccount(email: String, password: String) =
        repository.createAccount(email, password)

}