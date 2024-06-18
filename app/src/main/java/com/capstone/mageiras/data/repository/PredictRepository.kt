package com.capstone.mageiras.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.capstone.mageiras.data.remote.retrofit.ApiService
import com.google.gson.Gson
import okhttp3.MultipartBody
import retrofit2.HttpException
import com.capstone.mageiras.data.ResultState
import com.capstone.mageiras.data.remote.response.ErrorPredictResponse

class PredictRepository private constructor(
    private var apiService: ApiService
) {

    fun predictImage(image: MultipartBody.Part): LiveData<ResultState<List<String>>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.predictImage(image)
            emit(ResultState.Success(response.prediction))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val error = Gson().fromJson(jsonInString, ErrorPredictResponse::class.java)
            emit(ResultState.Error(error.error))
            println(e.message)
        } catch (e: Exception) {
            println(e.message) // Print the exception to the log
            emit(ResultState.Error("Error when caching API"))
        }
    }

    companion object {
        @Volatile
        private var instance: PredictRepository? = null

        fun getInstance(apiService: ApiService): PredictRepository =
            instance ?: synchronized(this) {
                instance ?: PredictRepository(
                    apiService
                ).also { instance = it }
            }
    }
}