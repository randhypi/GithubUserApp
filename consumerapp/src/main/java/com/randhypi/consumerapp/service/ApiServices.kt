package com.randhypi.consumerapp.service


import com.randhypi.consumerapp.model.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiServices {

    @GET("users")
    @Headers("Authorization: token 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")
    fun getUser(): Call<List<Users>>



}