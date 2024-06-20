package com.capstone.mageiras.data.remote.retrofit

import com.capstone.mageiras.data.remote.response.AddIngredientResponse
import com.capstone.mageiras.data.remote.response.IngredientResponse
import com.capstone.mageiras.data.remote.response.LoginResponse
import com.capstone.mageiras.data.remote.response.PredictResponse
import com.capstone.mageiras.data.remote.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("ml_api/predict_image")
    suspend fun predictImage(
        @Part file: MultipartBody.Part
    ): PredictResponse

    @Multipart
    @POST("ingredients/addmany")
    suspend fun addManyIngredients(
        @Header("Authorization") token: String,
        @Part("ingredients") ingredients: RequestBody
    ): AddIngredientResponse

//    @FormUrlEncoded
//    @POST("ingredients/add")
//    suspend fun addIngredient(
//        @Field("name") name: List<String>,
//        @Field("amount") amount: Int
//    ): AddIngredientResponse

    @Multipart
    @POST("login")
    suspend fun login(
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody
    ): LoginResponse

    @Multipart
    @POST("register")
    suspend fun register(
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("username") username: RequestBody
    ): RegisterResponse

    @GET("ingredients")
    suspend fun getIngredients(
        @Header("Authorization") token: String,
    ): IngredientResponse

}