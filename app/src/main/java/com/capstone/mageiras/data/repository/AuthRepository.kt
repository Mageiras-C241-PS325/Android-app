package com.capstone.mageiras.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


class AuthRepository(private var auth: FirebaseAuth) {


    fun createAccount(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun signIn(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        fun getInstance(auth: FirebaseAuth): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(auth).also { instance = it }
            }
    }
}