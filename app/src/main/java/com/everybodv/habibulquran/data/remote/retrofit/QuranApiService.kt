package com.everybodv.habibulquran.data.remote.retrofit

import com.everybodv.habibulquran.data.remote.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface QuranApiService {
    @GET("huruf")
    fun getAllHijaiyah(): Call<HijaiyahResponse>

    @GET("huruf/{id}")
    fun getHijaiyahById(
        @Path("id") id: Int
    ): Call<DataItem>

    @GET("surat")
    fun getAllSurah(): Call<SurahResponse>

    @GET("listsurat")
    fun getListSurah(): Call<SurahResponse>

    @GET("tes")
    fun getTadarusTest(): Call<SurahResponse>

    @GET("surat/{suratId}")
    fun getAyatBySurahId(
        @Path("suratId") suratId: Int
    ): Call<SurahByIdResponse>

    @GET("surat/{suratId}/{ayatId}")
    fun getAyatById(
        @Path("suratId") suratId: Int,
        @Path("ayatId") ayatId: Int
    ): Call<VersesItem>
}