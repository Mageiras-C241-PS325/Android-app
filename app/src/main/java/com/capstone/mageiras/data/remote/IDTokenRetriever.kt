package com.capstone.mageiras.data.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

data class TokenInfo(
    var token: String,
    var lastUpdate: LocalDateTime
)

val tokenInfoLiveData = MutableLiveData<TokenInfo?>()

fun getIdToken(): String {
    val tokenInfo = tokenInfoLiveData.value
    if (tokenInfo != null) {
        if (ChronoUnit.HOURS.between(
                tokenInfo.lastUpdate,
                LocalDateTime.now()
            ) >= 1 || tokenInfo.token == ""
        ) {
            // Refresh the token
            getToken(object : IDTokenCallback {
                override fun onTokenRetrieved(token: String?) {
                    if (token != null) {
                        tokenInfo.token = token
                    }
                    tokenInfo.lastUpdate = LocalDateTime.now()
                    tokenInfoLiveData.value = tokenInfo
                }
            })
        }
    } else {
        // Get the token for the first time
        getToken(object : IDTokenCallback {
            override fun onTokenRetrieved(token: String?) {
                if (token != null) {
                    tokenInfoLiveData.value = TokenInfo(token, LocalDateTime.now())
                }
            }
        })
    }
    Log.d("Token", tokenInfoLiveData.value?.token ?: "")
    return tokenInfoLiveData.value?.token ?: ""
}

private fun getToken(callback: IDTokenCallback) {
    Log.d("User", (Firebase.auth.currentUser ?: "").toString())
    Firebase.auth.currentUser?.getIdToken(true)?.addOnCompleteListener {
        if (it.isSuccessful) {
            callback.onTokenRetrieved(it.result?.token)
        } else {
            callback.onTokenRetrieved(null)
        }
    }
}

interface IDTokenCallback {
    fun onTokenRetrieved(token: String?)
}