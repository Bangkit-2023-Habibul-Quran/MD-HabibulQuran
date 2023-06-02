package com.everybodv.habibulquran.data.remote.retrofit

import com.everybodv.habibulquran.data.remote.response.GeneralResponse
import com.everybodv.habibulquran.data.remote.response.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface AuthApiService {

    @POST("login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @Multipart
    @POST("register")
    fun register(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part file: MultipartBody.Part,
        @Part("jenisKelamin") gender: RequestBody,
        @Part("birthdateYear") bdYear: Int,
        @Part("birthdateMonth") bdMonth: Int,
        @Part("birthdateDay") bdDay: Int
    ): Call<GeneralResponse>

    @POST("forget")
    fun forgotReset(
        @Field("email") email: String
    ): Call<GeneralResponse>

    @POST("verification")
    fun verifyAccount(
        @Field("email") email: String
    ): Call<GeneralResponse>

    @POST("edit")
    @FormUrlEncoded
    fun editProfile(
        @Query("id") id: String,
        @Field("user_id") userId: String = id,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("jenisKelamin") gender: String,
        @Field("birthdateYear") bdYear: Int,
        @Field("birthdateMonth") bdMonth: Int,
        @Field("birthdateDay") bdDay: Int
    ): Call<GeneralResponse>

}