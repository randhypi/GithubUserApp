package com.randhypi.consumerapp.data

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import com.randhypi.consumerapp.data.UserTable.Companion.CONTENT_URI
import com.randhypi.consumerapp.model.User
import com.randhypi.consumerapp.provider.ContentProviderLiveData

class UserSourceData(private val contentResolver: ContentResolver,private val context:Context) :ContentProviderLiveData<List<User>>(context,
    CONTENT_URI) {

   fun getUser(): List<User> {
        val result: MutableList<User> = mutableListOf()

        val cursor: Cursor? = contentResolver.query(
            CONTENT_URI, null, null,
            null, null
        )

        if (cursor?.moveToFirst() == true) {
            do {

                val id: Int = cursor.getInt(cursor.getColumnIndex(UserTable.ID))
                val username: String = cursor.getString(cursor.getColumnIndex(UserTable.USERNAME))
                val name: String = cursor.getString(cursor.getColumnIndex(UserTable.NAME))
                val avatar: String =
                    cursor.getString(cursor.getColumnIndex(UserTable.AVATAR))
                val user =  User(id,username,name,avatar)
                result.add(user)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return result.toList()
        }

    override fun getContentProviderValue(): List<User> = getUser()
}






