package com.everybodv.habibulquran.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Quran (
    val titleSurah: String,
    val totalAyat: Int,
    val loc: String,
    val surahAyat: List<SurahAyat>
) : Parcelable

@Parcelize
data class SurahAyat(
    val numAyat: Int,
    val ayat: String,
    val latin: String,
    val translate: String
) : Parcelable