package com.everybodv.habibulquran.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class LoginResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("user")
	val user: User
) : Parcelable

@Parcelize
data class User(

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("jenisKelamin")
	val jenisKelamin: String,

	@field:SerializedName("birthdate")
	val birthdate: String,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("is_verified")
	val isVerified: Int,

	@field:SerializedName("token")
	val token: String
) : Parcelable
