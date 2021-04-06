package com.randhypi.githubuserapp.vm


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.randhypi.githubuserapp.model.ResponseSearchUser
import com.randhypi.githubuserapp.model.User
import com.randhypi.githubuserapp.model.Users
import com.randhypi.githubuserapp.service.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class MainViewModel : ViewModel() {


    companion object {
        val TAG = MainViewModel::class.java.simpleName

    }

    val _listUsers = MutableLiveData<ArrayList<User>>()

    val retrofitUserAll: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun setUserAll() {
        val listUser = ArrayList<User>()

        var serviceUser: ApiServices = retrofitUserAll.create(ApiServices::class.java)
        val callUser: Call<List<Users>> = serviceUser.getUser()
        callUser.enqueue(object : Callback<List<Users>> {
            override fun onResponse(
                call: Call<List<Users>>,
                response: Response<List<Users>>
            ) {
                try {
                    if (!response.isSuccessful) {
                        Log.d(TAG, response.code().toString())
                        return
                    }
                    val response1 = response.body()
                    for (i in 0 until response1?.size!!) {
                            val name = response1?.get(i).login
                            val ava = response1?.get(i).avatarUrl
                            val user = User(name = name, avatar = ava)
                            listUser.add(user)
                    }
                    _listUsers.postValue(listUser)
                } catch (e: Exception) {
                    Log.d(TAG, e.toString())
                }
            }
            override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                Log.d(TAG, t.toString())
            }
        })


    }

    fun setSearchUser(user: String){
        val listUser = ArrayList<User>()

        var serviceUser: ApiServices = retrofitUserAll.create(ApiServices::class.java)
        val callUser: Call<ResponseSearchUser> = serviceUser.getSearchUser(user = user)
        callUser.enqueue(object : Callback<ResponseSearchUser> {
            override fun onResponse(
                call: Call<ResponseSearchUser>,
                response: Response<ResponseSearchUser>
            ) {
                try {
                    if (!response.isSuccessful) {
                        Log.d(TAG, response.code().toString())
                        return
                    }
                    val response1 = response.body()?.items
                    for (i in 0 until  response1?.size!!){
                        val name = response1?.get(i)?.login
                        val ava = response1?.get(i)?.avatarUrl
                        val user = User(name = name, avatar = ava)
                        listUser.add(user)
                    }


                    _listUsers.postValue(listUser)
                } catch (e: Exception) {
                    Log.d(TAG, e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseSearchUser>, t: Throwable) {
                Log.d(TAG, t.toString())
            }
        })
    }


    fun getUserAll(): LiveData<ArrayList<User>> {
        return _listUsers
    }
}



