package com.randhypi.githubuserapp.data

import android.content.ContentResolver
import android.database.Cursor
import com.randhypi.githubuserapp.data.UserTable.Companion.CONTENT_URI
import com.randhypi.githubuserapp.model.User

class UserSourceData(private val contentResolver: ContentResolver) {

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

    }






