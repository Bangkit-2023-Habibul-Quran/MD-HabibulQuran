package com.everybodv.habibulquran.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class QuranPredictResponse(

	@field:SerializedName("Rating")
	val rating: Int,

	@field:SerializedName("Predicted transcription")
	val predictedTranscription: String
) : Parcelable
