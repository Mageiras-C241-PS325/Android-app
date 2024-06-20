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

        val token = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImRmOGIxNTFiY2Q5MGQ1YjMwMjBlNTNhMzYyZTRiMzA3NTYzMzdhNjEiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vbWFnZWlyYXMtOTkwNDIiLCJhdWQiOiJtYWdlaXJhcy05OTA0MiIsImF1dGhfdGltZSI6MTcxODg2MDUyNSwidXNlcl9pZCI6ImRkV09mR1NudHhmVlhVY3BwTHptMzkzN0NlbjIiLCJzdWIiOiJkZFdPZkdTbnR4ZlZYVWNwcEx6bTM5MzdDZW4yIiwiaWF0IjoxNzE4ODYwNTI1LCJleHAiOjE3MTg4NjQxMjUsImVtYWlsIjoiamVqZUBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZW1haWwiOlsiamVqZUBnbWFpbC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.olAHTTTWZnhHn0IEqzcmeQD00bmAterWz0Z1LzS11jt8YPZ4MkHsIg2t67HeLuglF3jyMAW1wWRUD7lQ1Fmv5BVCL4bZFvcTr4VFoO4ObiBrSXg9Y1AlwDeFiQP3bQ2QMAIQHd5J8WLYRxAQfTveOleWz3V4zj4NpPVZALWme5VrUFD0wjp-VUHm7jsNWFlt_RnJ51KflVIc1fYBbNcRALa9c_N806i6Z5nHe1X6nuPzrsHg1mrzVfn3U4AiGGZWtGGkqbEXk3J-M2n7lJgUyY5nQr9mWAbEPu5h5XcYMeeRLviD32Ei5A5lVzSClLcQquiXejTSntpisJz91yGn8Q"
        val apiService = ApiConfig.getApiService(token)
        return IngredientRepository.getInstance(apiService)
    }

}