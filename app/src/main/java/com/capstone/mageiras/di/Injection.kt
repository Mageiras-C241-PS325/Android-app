package com.capstone.mageiras.di

import android.util.Log
import com.capstone.mageiras.data.remote.IDTokenCallback
import com.capstone.mageiras.data.remote.idTokenRetriever
import com.capstone.mageiras.data.remote.retrofit.ApiConfig
import com.capstone.mageiras.data.repository.AuthRepository
import com.capstone.mageiras.data.repository.IngredientRepository
import com.capstone.mageiras.data.repository.PredictRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

object Injection {
    fun provideRepository(): AuthRepository {
        val auth: FirebaseAuth = Firebase.auth
        val idTokenCallback = object : IDTokenCallback {
            override fun onCallback(idToken: String?) {
                Log.d("authrepo", idToken.toString())
            }
        }
        val idToken = idTokenRetriever(
            idTokenCallback
        ).toString()
        val apiService = ApiConfig.getApiService(idToken)
        return AuthRepository.getInstance(auth,apiService)
    }

    fun providePredictRepository(): PredictRepository {
        val apiService = ApiConfig.getMLApiConfig()
        return PredictRepository.getInstance(apiService)
    }

    fun provideIngredientRepository(): IngredientRepository {
        val idTokenCallback = object : IDTokenCallback {
            override fun onCallback(idToken: String?) {
                Log.d("ingrerepo", idToken.toString())
            }
        }
        val idToken = idTokenRetriever(
            idTokenCallback
        ).toString()

        val token = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImRmOGIxNTFiY2Q5MGQ1YjMwMjBlNTNhMzYyZTRiMzA3NTYzMzdhNjEiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vbWFnZWlyYXMtOTkwNDIiLCJhdWQiOiJtYWdlaXJhcy05OTA0MiIsImF1dGhfdGltZSI6MTcxODg0ODg4MiwidXNlcl9pZCI6ImRkV09mR1NudHhmVlhVY3BwTHptMzkzN0NlbjIiLCJzdWIiOiJkZFdPZkdTbnR4ZlZYVWNwcEx6bTM5MzdDZW4yIiwiaWF0IjoxNzE4ODQ4ODgyLCJleHAiOjE3MTg4NTI0ODIsImVtYWlsIjoiamVqZUBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZW1haWwiOlsiamVqZUBnbWFpbC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.OVkbu6hIVUIf04bU8NDql2VaGW6yf4_Jcu_GrePPu9x4L0MbkeNy_zDgclOLdgKTeI7IavbVTZ7QZOtaoJF4V9gg4DsVpwxUSunGrkV-jqa6rrI7e9KcW4wLsxNQ4aM-4OGrrh6MZq2ayrsyM48d-PzokSH-fYtUOhCZ3lUdp-nz8RBNOl8hNFunuQ91NucgyzmiXGUlfDzqh7A7m8xb8A_fu_diR_q2A2nDJeMpC17dbVLWhNmh29-kiIc7xAHbkWlMXE0S_19Uo1DJ1B6hb64IK4Ezp29Ra-vgEC0MlcWWfJ4yNR9Zbl0oiNIF6HFdApXuO0hMOD9sCwxluH6mbQ"
        val apiService = ApiConfig.getApiService(token)
        return IngredientRepository.getInstance(apiService)
    }

}