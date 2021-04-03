package com.randhypi.githubuserapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    var id: Int? = 0,
    var username: String? = "",
    var name: String? = "",
    var avatar: String? = "",
    var follower: List<User> =  arrayListOf(),
    var following: List<User> = arrayListOf(),
) : Parcelable



