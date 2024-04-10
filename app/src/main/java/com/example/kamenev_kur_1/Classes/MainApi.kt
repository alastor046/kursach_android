package com.example.kamenev_kur_1.Classes


import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MainApi {

    @POST("Users")
    suspend fun postLoginData(@Body authData: AuthData): Users

    @POST("Users")
    suspend fun regUser(@Body users: Users) :ResponseBody
}