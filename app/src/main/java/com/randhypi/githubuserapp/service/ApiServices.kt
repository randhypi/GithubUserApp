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