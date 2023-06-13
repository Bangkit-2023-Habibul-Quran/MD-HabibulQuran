package com.everybodv.habibulquran.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class QuranPredictResponse(

	@field:SerializedName("Predicted transcription")
	val predictedTranscription: String? = null,

	@field:SerializedName("Rating")
	val rating: Int? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null

) : Parcelable
