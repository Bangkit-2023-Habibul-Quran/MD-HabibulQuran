package com.everybodv.habibulquran.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class SurahResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: List<Data>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

@Parcelize
data class Translation(

	@field:SerializedName("en")
	val en: String? = null,

	@field:SerializedName("id")
	val id: String
) : Parcelable

@Parcelize
data class Text(

	@field:SerializedName("transliteration")
	val transliteration: Transliteration? = null,

	@field:SerializedName("arab")
	val arab: String
) : Parcelable

@Parcelize
data class Name(

	@field:SerializedName("short")
	val jsonMemberShort: String,

	@field:SerializedName("transliteration")
	val transliteration: Transliteration? = null
) : Parcelable

@Parcelize
data class VersesItem(

	@field:SerializedName("number")
	val number: Number,

	@field:SerializedName("translation")
	val translation: Translation? = null,

	@field:SerializedName("text")
	val text: Text,

	@field:SerializedName("audio")
	val audio: Audio
) : Parcelable

@Parcelize
data class Audio(

	@field:SerializedName("secondary")
	val secondary: List<String>,

	@field:SerializedName("primary")
	val primary: String
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("number")
	val number: Int,

	@field:SerializedName("numberOfVerses")
	val numberOfVerses: Int,

	@field:SerializedName("name")
	val name: Name,

	@field:SerializedName("preBismillah")
	val preBismillah: PreBismillah? = null,

	@field:SerializedName("verses")
	val verses: List<VersesItem>? = null
) : Parcelable

@Parcelize
data class Transliteration(

	@field:SerializedName("en")
	val en: String? = null,

	@field:SerializedName("id")
	val id: String? = null
) : Parcelable

@Parcelize
data class PreBismillah(

	@field:SerializedName("translation")
	val translation: Translation,

	@field:SerializedName("text")
	val text: Text,

	@field:SerializedName("audio")
	val audio: Audio
) : Parcelable

@Parcelize
data class Number(

	@field:SerializedName("inSurah")
	val inSurah: Int
) : Parcelable
