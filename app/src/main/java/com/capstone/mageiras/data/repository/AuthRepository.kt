package com.capstone.mageiras.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.capstone.mageiras.data.Result
import com.capstone.mageiras.data.remote.response.LoginResponse
import com.capstone.mageiras.data.remote.response.RegisterResponse
import com.capstone.mageiras.data.remote.retrofit.ApiService
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import okhttp3.RequestBody
import retrofit2.HttpException

class AuthRepository(private var auth: FirebaseAuth, private var apiService: ApiService) {


    fun createAccount(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun signIn(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun register(
        email: RequestBody,
        password: RequestBody,
        username: RequestBody
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(email, password, username)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            emit(Result.Error(jsonInString!!))
            println(e.message)
        } catch (e: Exception) {
            println(e.message) // Print the exception to the log
            emit(Result.Error("Error when caching API"))
        }
    }

    fun login(email: RequestBody, password: RequestBody): LiveData<Result<LoginResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.login(email, password)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                emit(Result.Error(jsonInString!!))
                println(e.message)
            } catch (e: Exception) {
                println(e.message) // Print the exception to the log
                emit(Result.Error("Error when caching API"))
            }
        }


    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        fun getInstance(auth: FirebaseAuth, apiService: ApiService): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(auth, apiService).also { instance = it }
            }
    }
}