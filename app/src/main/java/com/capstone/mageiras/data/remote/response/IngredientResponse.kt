package com.capstone.mageiras.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class IngredientResponse(

	@field:SerializedName("ingredients")
	val ingredients: List<IngredientsItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
@Parcelize
data class IngredientsItem(

	@field:SerializedName("amount")
	var amount: Int? = null,

	@field:SerializedName("last_update")
	val lastUpdate: String? = null,

	@field:SerializedName("name")
	var name: String? = null,

	@field:SerializedName("id")
	val id: String? = null

) : Parcelable
