package com.example.githubuserapp.vm

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject


class MainViewModel : ViewModel() {

    companion object {
        val TAG = MainViewModel::class.java.simpleName

    }

     val _listUsers = MutableLiveData<ArrayList<User>>()

     fun setUser() {
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
                    val response = JSONArray(result)
                    for (i in 0 until response.length()) {
                        response?.let {
                            val name = it.getJSONObject(i).getString("login")
                            val ava = it.getJSONObject(i).getString("avatar_url")
                            val user = User(name = name, avatar = ava)
                            listUser.add(user)
                        }

                    }
                    _listUsers.postValue(listUser)
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

    fun setUserAll(username: String) {
        val listUser = ArrayList<User>()

        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "fecdd17f1dd5ed66cf5d9e60e4e3a047d72d45dd")
        asyncClient.addHeader("User-Agent", "request")

        asyncClient.get("https://api.github.com/search/users?q=$username", object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = if (responseBody != null) String(responseBody) else null
                Log.d(TAG, result)
                try {
                    val jsonObject= JSONObject(result)
                    val jsonArray = jsonObject.getJSONArray("items")
                    for (i in 0 until jsonArray.length()) {
                        jsonArray?.let {
                            val name = it.getJSONObject(i).getString("login")
                            val ava = it.getJSONObject(i).getString("avatar_url")
                            val user = User(name = name, avatar = ava)
                            Log.d(TAG,user.name)
                            listUser.add(user)
                        }
                    }
                    _listUsers.postValue(listUser)
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
        return _listUsers
    }
}

