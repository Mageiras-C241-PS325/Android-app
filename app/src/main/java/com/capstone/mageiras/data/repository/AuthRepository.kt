package com.capstone.mageiras.data.repository

import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.capstone.mageiras.ui.main.MainActivity
import com.capstone.mageiras.ui.welcome.WelcomeActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

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