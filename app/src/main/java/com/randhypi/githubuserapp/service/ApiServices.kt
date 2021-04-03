package com.randhypi.githubuserapp.service


import com.randhypi.githubuserapp.model.ResponseSearchUser
import com.randhypi.githubuserapp.model.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiServices {

    @GET("users")
    @Headers("Authorization: token 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")
    fun getUser(): Call<List<Users>>


    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUser(@Query("q") user: String): Call<ResponseSearchUser>


}