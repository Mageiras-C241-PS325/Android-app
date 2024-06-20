package com.capstone.mageiras.ui.login

import androidx.lifecycle.ViewModel
import com.capstone.mageiras.data.repository.AuthRepository
import okhttp3.RequestBody

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    fun signIn(email: String, password: String) =
        repository.signIn(email, password)

    fun login(email: RequestBody, password: RequestBody) = repository.login(email, password)

}