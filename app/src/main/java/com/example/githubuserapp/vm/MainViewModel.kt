package com.example.githubuserapp.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.model.Response
import com.example.githubuserapp.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import cz.msebera.android.httpclient.Header
import java.lang.reflect.Type



class MainViewModel : ViewModel() {

    companion object {
        val TAG = MainViewModel::class.java.simpleName

    }

    val listUsers = MutableLiveData<ArrayList<User>>()


    fun setUserAll() {

        val listUser = ArrayList<User>()

        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "fecdd17f1dd5ed66cf5d9e60e4e3a047d72d45dd")
        asyncClient.addHeader("User-Agent", "request")

        asyncClient.get("https://api.github.com/users", object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = if (responseBody != null) String(responseBody) else null
                Log.d(TAG, result)
                try {

                    val moshi = Moshi.Builder()
                        .addLast(KotlinJsonAdapterFactory())
                        .build()

                    val jsonAdapter = moshi.adapter(Response::class.java)
                    val response = jsonAdapter.fromJson(result)


                    for (i in 0 until responseBody?.size!!) {
                        response?.let {
                            val name = it.login
                            val ava = it.avatar_url
                            val user = User(name = name, avatar = ava)
                            Log.d(TAG, user.name.toString())
                            listUser.add(user)
                        }
                    }
                    listUsers.postValue(listUser)
                } catch (e: Exception) {
                    Log.d(TAG, e.toString())
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

    fun getUserAll(): LiveData<ArrayList<User>> {
        return listUsers
    }
}