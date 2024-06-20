package com.capstone.mageiras.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("idToken")
	val idToken: String
)
