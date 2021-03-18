package com.example.githubuserapp.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.model.Response
import com.example.githubuserapp.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import cz.msebera.android.httpclient.Header
import java.lang.Exception

class DetailViewModel : ViewModel() {
    companion object {
        val TAG = MainViewModel::class.java.simpleName

    }

    val userDetail = MutableLiveData<ArrayList<User>>()


    fun setDetailUser(username: String) {

        val detailUser = ArrayList<User>()

        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "fecdd17f1dd5ed66cf5d9e60e4e3a047d72d45dd")
        asyncClient.addHeader("User-Agent", "request")

        asyncClient.get("https://api.github.com/users/{$username}", object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = if (responseBody != null) String(responseBody) else null
                Log.d(TAG,result.toString())

                try {

                    val user = User()

                    val moshi = Moshi.Builder()
                        .addLast(KotlinJsonAdapterFactory())
                        .build()
                    val jsonAdapter = moshi.adapter(Response::class.java)
                    val response = jsonAdapter.fromJson(result)



                        response?.let {
                            val name = it.login
                            val ava = it.avatar_url

                            user.avatar = ava.toString()
                            user.name = name.toString()

                            detailUser.add(user)
                        }



                    userDetail.postValue(detailUser)
                }catch (e: Exception){
                    Log.d(TAG,e.toString())

                }


            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("onFailure", error!!.message.toString())
            }

        })


    }


    fun getUserDetail(): LiveData<ArrayList<User>> {
        return userDetail
    }
}