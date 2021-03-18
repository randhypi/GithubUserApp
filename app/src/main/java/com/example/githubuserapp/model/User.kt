package com.example.githubuserapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@kotlinx.parcelize.Parcelize
data class User(
    var username: String? = "",
    var name: String? = "",
    var avatar: String? = "",
    var follower: String? = "0",
    var following: String? = "0",
    var company: String? = "",
    var location: String? = "",
    var repository: String? = ""
) : Parcelable


