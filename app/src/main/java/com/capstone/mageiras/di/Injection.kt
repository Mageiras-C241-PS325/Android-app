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

        val token = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImRmOGIxNTFiY2Q5MGQ1YjMwMjBlNTNhMzYyZTRiMzA3NTYzMzdhNjEiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vbWFnZWlyYXMtOTkwNDIiLCJhdWQiOiJtYWdlaXJhcy05OTA0MiIsImF1dGhfdGltZSI6MTcxODg2NDM2NCwidXNlcl9pZCI6ImRkV09mR1NudHhmVlhVY3BwTHptMzkzN0NlbjIiLCJzdWIiOiJkZFdPZkdTbnR4ZlZYVWNwcEx6bTM5MzdDZW4yIiwiaWF0IjoxNzE4ODY0MzY0LCJleHAiOjE3MTg4Njc5NjQsImVtYWlsIjoiamVqZUBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZW1haWwiOlsiamVqZUBnbWFpbC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.Rfv1Lo-YkM1CIXXLBdaEO4YSAtFOynAu_GGR9EMGPoaGMSUbC--Cz2itlJKhtnw3NK5qjkk1YDlZW_nxc-4YZD_C_gsbe0uL306nFRDJJPGeAGU-meB_IsywGS3fvW7j9oRKr-ApmxqlPaafna69M-SA83MlOnvNnrwx403EMQ03L_6cRsD4zovMgi_u0vs9zCXK1atB5X7dmrrzY5jaGaAScjPs7OG89N2tsd_5hbBdLwg5U4qC1ialt-PkPoYf7DTecyl3mCs7GaLQRqYlh2U8CNlKQfELBp9Blj7sHDzCn1dt-JFOgKEt6_uaEfGFlO5eHh1BC6QiLPaKFulnhQ"
        val apiService = ApiConfig.getApiService(token)
        return IngredientRepository.getInstance(apiService)
    }

}