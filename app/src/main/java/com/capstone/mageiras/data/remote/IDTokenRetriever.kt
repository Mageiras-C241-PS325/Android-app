package com.capstone.mageiras.data.remote

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

fun idTokenRetriever(callback: IDTokenCallback) {
    Firebase.auth.currentUser?.getIdToken(true)?.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val idToken = task.result?.token
            callback.onCallback(idToken)
        } else {
            Log.d("idToken", "Failed to get idToken")
            callback.onCallback(null)
        }
    }
}

interface IDTokenCallback {
    fun onCallback(idToken: String?)
}