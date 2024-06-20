package com.capstone.mageiras.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.capstone.mageiras.data.Result
import com.capstone.mageiras.data.remote.response.AddIngredientResponse
import com.capstone.mageiras.data.remote.response.ErrorPredictResponse
import com.capstone.mageiras.data.remote.response.IngredientsItem
import com.capstone.mageiras.data.remote.response.RecipesItem
import com.capstone.mageiras.data.remote.retrofit.ApiService
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class IngredientRepository private constructor(
    private var apiService: ApiService
) {

//    fun addIngredient(name: String, amount: Int): LiveData<Result<String>> = liveData {
//        emit(Result.Loading)
//        try {
//            val response = apiService.addIngredient(name, amount)
//            emit(Result.Success(response.message))
//        } catch (e: HttpException) {
//            val jsonInString = e.response()?.errorBody()?.string()
////            val error = Gson().fromJson(jsonInString, ErrorPredictResponse::class.java)
//            emit(Result.Error(jsonInString!!))
//            println(e.message)
//        } catch (e: Exception) {
//            println(e.message) // Print the exception to the log
//            emit(Result.Error("Error when caching API"))
//        }
//    }

    fun addManyIngredients(ingredients: RequestBody): LiveData<Result<AddIngredientResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.addManyIngredients(ingredients)
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

    fun getIngredients(): LiveData<Result<List<IngredientsItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getIngredients()
            emit(Result.Success(response.ingredients))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            emit(Result.Error(jsonInString!!))
            println(e.message)
        } catch (e: Exception) {
            println(e.message) // Print the exception to the log
            emit(Result.Error("Error when caching API"))
        }
    }

    fun getRecommendRecipes(): LiveData<Result<List<RecipesItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getRecommendRecipe()
            emit(Result.Success(response.recipes))
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
        private var instance: IngredientRepository? = null

        fun getInstance(apiService: ApiService): IngredientRepository =
            instance ?: synchronized(this) {
                instance ?: IngredientRepository(
                    apiService
                ).also { instance = it }
            }
    }
}