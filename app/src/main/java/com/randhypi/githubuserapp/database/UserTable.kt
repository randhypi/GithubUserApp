package com.randhypi.githubuserapp.database

import android.content.ContentValues
import android.net.Uri
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "userTable")
data class UserTable(
    @ColumnInfo(name = "username") var username: String? = "",
    @ColumnInfo(name = "name") var name: String? = "",
    @ColumnInfo(name = "avatar") var avatar: String? = "",
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int
) : Parcelable {


    companion object{
        const val TABLE_NAME = "userTable"
        const val ID = "id"
        const val USERNAME = "username"
        const val NAME = "name"
        const val AVATAR = "avatar"

        const val AUTHORITY = "com.randhypi.githubuserapp"
        const val SCHEME = "content"

        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()

        fun fromContentValues(contentValues: ContentValues): UserTable? {
            val userTable = UserTable(id = 0)
            when{
                contentValues.containsKey(USERNAME) ->  userTable.username = contentValues.getAsString(USERNAME)
                contentValues.containsKey(NAME) ->  userTable.name = contentValues.getAsString(NAME)
                contentValues.containsKey(AVATAR) ->  userTable.avatar =  contentValues.getAsString(AVATAR)
            }
            return userTable
        }
    }
}
