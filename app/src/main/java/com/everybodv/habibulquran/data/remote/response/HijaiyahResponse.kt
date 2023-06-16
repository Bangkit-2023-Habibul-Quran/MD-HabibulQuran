package com.everybodv.habibulquran.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class HijaiyahResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
) : Parcelable

@Parcelize
data class DataItem(

	@field:SerializedName("arabic")
	val arabic: String,

	@field:SerializedName("pronounciation")
	val pronounciation: String,

	@field:SerializedName("audio")
	val audio: String,

	@field:SerializedName("prediction")
	val prediction: String,

	@field:SerializedName("available")
	val available: Boolean
) : Parcelable
