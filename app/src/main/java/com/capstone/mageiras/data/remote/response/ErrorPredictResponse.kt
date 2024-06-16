package com.capstone.mageiras.data.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorPredictResponse(

	@field:SerializedName("error")
	val error: String
)
