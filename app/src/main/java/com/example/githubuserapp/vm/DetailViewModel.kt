package com.example.githubuserapp.vm


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


class DetailViewModel : ViewModel() {
    companion object {
        val TAG = DetailViewModel::class.java.simpleName

    }

    val followingDetail = MutableLiveData<ArrayList<User>>()
    val followersDetail = MutableLiveData<ArrayList<User>>()
    val userDetail = MutableLiveData<ArrayList<User>>()

    fun setFollowing(username: String) {

        val followingsUser = ArrayList<User>()

        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "fecdd17f1dd5ed66cf5d9e60e4e3a047d72d45dd")
        asyncClient.addHeader("User-Agent", "request")

        asyncClient.get(
            "https://api.github.com/users/$username/following",
            object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray
                ) {
                    val result = String(responseBody)
                    Log.d(MainViewModel.TAG, result)
                    try {
                        val response = JSONArray(result)
                        for (i in 0 until response.length()) {
                            response.let {
                                val name = it.getJSONObject(i).getString("login")
                                val ava = it.getJSONObject(i).getString("avatar_url")
                                val user = User(name = name, avatar = ava)
                                followingsUser.add(user)
                            }
                        }
                        followingDetail.postValue(followingsUser)
                    } catch (e: Exception) {
                        Log.d(MainViewModel.TAG, e.toString())
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

    fun getFollowing(): LiveData<ArrayList<User>> {
        return followingDetail
    }


    fun setFollowers(username: String) {

        val followersUser = ArrayList<User>()

        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "fecdd17f1dd5ed66cf5d9e60e4e3a047d72d45dd")
        asyncClient.addHeader("User-Agent", "request")

        asyncClient.get(
            "https://api.github.com/users/$username/followers",
            object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray
                ) {
                    val result =String(responseBody)
                    Log.d(MainViewModel.TAG, result)
                    try {
                        val response = JSONArray(result)
                        for (i in 0 until response.length()) {
                            response.let {
                                val name = it.getJSONObject(i).getString("login")
                                val ava = it.getJSONObject(i).getString("avatar_url")
                                val user = User(name = name, avatar = ava)
                                followersUser.add(user)
                            }

                        }
                        followersDetail.postValue(followersUser)
                    } catch (e: Exception) {
                        Log.d(MainViewModel.TAG, e.toString())
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

    fun getFollowers(): LiveData<ArrayList<User>> {
        return followersDetail
    }

    fun setUserDetail(username: String) {

        val detailUser = ArrayList<User>()

        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "fecdd17f1dd5ed66cf5d9e60e4e3a047d72d45dd")
        asyncClient.addHeader("User-Agent", "request")

        asyncClient.get(
            "https://api.github.com/users/$username",
            object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray
                ) {
                    val result = String(responseBody)
                    Log.d(TAG, result)
                    try {
                        val response = JSONObject(result)

                        val name = response.getString("name")
                        val username1 = response.getString("login")
                        val avatar = response.getString("avatar_url")


                        val user = User(name = name, username = username1, avatar = avatar)

                        detailUser.add(user)
                        userDetail.postValue(detailUser)
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

    fun getUserDetail(): LiveData<ArrayList<User>> {
        return userDetail
    }
}