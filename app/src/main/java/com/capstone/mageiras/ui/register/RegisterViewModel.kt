package com.capstone.mageiras.ui.register

import androidx.lifecycle.ViewModel
import com.capstone.mageiras.data.repository.AuthRepository
import okhttp3.RequestBody

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {

    fun createAccount(email: String, password: String) =
        repository.createAccount(email, password)

    fun register(email: RequestBody, password: RequestBody, username: RequestBody) = repository.register(email, password, username)

}