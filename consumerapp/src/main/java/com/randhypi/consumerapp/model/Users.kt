package com.randhypi.consumerapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Users(

	@field:SerializedName("avatar_url")
	var avatarUrl: String? = null,

	@field:SerializedName("id")
	var id: Int? = null,

	@field:SerializedName("login")
	var login: String? = null
) : Parcelable
