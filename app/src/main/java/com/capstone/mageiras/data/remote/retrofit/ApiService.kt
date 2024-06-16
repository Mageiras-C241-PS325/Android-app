package com.capstone.mageiras.data.remote.retrofit

import com.capstone.mageiras.data.remote.response.PredictResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("ml_api/predict_image")
    suspend fun predictImage(
        @Part file: MultipartBody.Part
    ): PredictResponse

}