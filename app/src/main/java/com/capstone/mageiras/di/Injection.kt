package com.capstone.mageiras.di

import android.content.Context
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

        val token = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImRmOGIxNTFiY2Q5MGQ1YjMwMjBlNTNhMzYyZTRiMzA3NTYzMzdhNjEiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vbWFnZWlyYXMtOTkwNDIiLCJhdWQiOiJtYWdlaXJhcy05OTA0MiIsImF1dGhfdGltZSI6MTcxODg0NDU1MSwidXNlcl9pZCI6ImRkV09mR1NudHhmVlhVY3BwTHptMzkzN0NlbjIiLCJzdWIiOiJkZFdPZkdTbnR4ZlZYVWNwcEx6bTM5MzdDZW4yIiwiaWF0IjoxNzE4ODQ0NTUxLCJleHAiOjE3MTg4NDgxNTEsImVtYWlsIjoiamVqZUBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZW1haWwiOlsiamVqZUBnbWFpbC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.J7rEuDfEmsg5vGeLFSn8XiDWqqhEE2OLNJ6hy9Zh0XnVUWk2wQomGLn06X4k7zvputU784M8C_WiFX6N-9NTI07jk9S_-9H2OLWibgc-gAsKLrJ83Z579KhAs-UmdzPXvL8KST1VNco_-GhItlfM_xJ2epKdqQnJ-8i8wDxHMru7Fi2wqt1rdSlePCVkjlCU8pQNN8TDLf7gSuBh_XBylR31LFGq0D5RMXdxJvi6r8gtTfXE8R03c16G0TCjWH1JZs-yUbAxaZm-l_D-M6Kr2yE9k_jE92RgglsN1E892NlTbulXRZeaAajbWxIlHXuizo5nF1Yfe3S8LOIBq9tNtg"
        val apiService = ApiConfig.getApiService(token)
        return IngredientRepository.getInstance(apiService)
    }

}