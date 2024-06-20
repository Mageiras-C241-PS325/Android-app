package com.capstone.mageiras.data.remote.response

import com.google.gson.annotations.SerializedName

data class IngredientResponse(

	@field:SerializedName("ingredients")
	val ingredients: List<IngredientsItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class IngredientsItem(

	@field:SerializedName("amount")
	var amount: Int? = null,

	@field:SerializedName("last_update")
	val lastUpdate: String? = null,

	@field:SerializedName("name")
	var name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
