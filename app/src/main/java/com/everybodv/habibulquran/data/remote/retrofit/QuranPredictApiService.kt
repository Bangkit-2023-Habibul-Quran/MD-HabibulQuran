package com.everybodv.habibulquran.data.remote.retrofit

import com.everybodv.habibulquran.data.remote.response.QuranPredictResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface QuranPredictApiService {

    @Multipart
    @POST("predict")
    fun predictTadarus(
        @Part file: MultipartBody.Part,
        @Part("original_text") originalText: RequestBody
    ): Call<QuranPredictResponse>

}