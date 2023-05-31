package com.everybodv.habibulquran.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tadarus (
    val surah: String,
    val totalAyat: Int
) : Parcelable