package com.randhypi.githubuserapp.vm

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.randhypi.githubuserapp.database.UserTable
import com.randhypi.githubuserapp.database.UserTable.Companion.AVATAR
import com.randhypi.githubuserapp.database.UserTable.Companion.CONTENT_URI
import com.randhypi.githubuserapp.database.UserTable.Companion.NAME
import com.randhypi.githubuserapp.database.UserTable.Companion.USERNAME

class DatabaseViewModel(private val mContext: Context): ViewModel() {
    private var contentResolver: ContentResolver = mContext.contentResolver

    val allUserTable = MutableLiveData<ArrayList<UserTable>>()

    fun setAllUserTable(){
            val userList = ArrayList<UserTable>()
            val cursor = contentResolver.query(UserTable.CONTENT_URI, null, null, null, null)
            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val userTable = UserTable()
                    userTable.id = cursor.getInt(0)
                    userTable.username = cursor.getString(1)
                    userTable.name = cursor.getString(2)
                    userTable.avatar = cursor.getString(3)

                    userList.add(userTable)
                }
            }
            allUserTable.postValue(userList)
    }

    fun getAllUserTable(): LiveData<ArrayList<UserTable>> {
        return allUserTable
    }

    fun insertUserTable(userTable: UserTable) {
        val contentValues = ContentValues()
        contentValues.put(USERNAME, userTable.username)
        contentValues.put(NAME, userTable.name)
        contentValues.put(AVATAR, userTable.avatar)
        contentResolver.insert(CONTENT_URI, contentValues)
    }


}