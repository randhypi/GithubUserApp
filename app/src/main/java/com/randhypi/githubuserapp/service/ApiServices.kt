package com.randhypi.githubuserapp.service


import com.randhypi.githubuserapp.model.ResponseSearchUser
import com.randhypi.githubuserapp.model.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiServices  {
    @Headers("Cache-Control: max-age=640000","Authorization: fecdd17f1dd5ed66cf5d9e60e4e3a047d72d45dd")
    @GET("users")
    fun getUser(): Call<List<Users>>

    @Headers("Cache-Control: max-age=640000","Authorization: fecdd17f1dd5ed66cf5d9e60e4e3a047d72d45dd")
    @GET("search/users")
    fun getSearchUser(@Query("q") user: String): Call<ResponseSearchUser>


}