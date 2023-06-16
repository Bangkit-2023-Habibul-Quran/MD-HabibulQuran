package com.everybodv.habibulquran.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DetailUserResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("user")
	val user: UserData
) : Parcelable

@Parcelize
data class UserData(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("birthdate")
	val birthdate: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("jenisKelamin")
	val jenisKelamin: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("is_verified")
	val isVerified: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
) : Parcelable
