package com.everybodv.habibulquran.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hijaiyah(
    val id: Int,
    val letter: String,
    val pronounce: String
) : Parcelable