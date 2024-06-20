package com.capstone.mageiras.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

data class RecipeResponse(

	@field:SerializedName("recipes")
	val recipes: List<RecipesItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

@Parcelize
data class RecipesItem(

	@field:SerializedName("directions")
	val directions: String,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("genre")
	val genre: String,

	@field:SerializedName("ingredients")
	val ingredients: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("label")
	val label: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("ingredient_match")
	val ingredientMatch: Int
): Parcelable
