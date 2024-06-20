package com.capstone.mageiras.data.remote.response

import com.google.gson.annotations.SerializedName

data class AddIngredientResponse(

	@field:SerializedName("ingredients")
	val ingredients: List<String>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
