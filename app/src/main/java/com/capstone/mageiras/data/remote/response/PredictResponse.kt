package com.capstone.mageiras.data.remote.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("prediction")
	val prediction: List<String>

)
