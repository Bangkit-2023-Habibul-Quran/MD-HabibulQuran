package com.everybodv.habibulquran.data.remote.response

import com.google.gson.annotations.SerializedName

data class HijaiyahPredictResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("Predicted label")
	val predictedLabel: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
