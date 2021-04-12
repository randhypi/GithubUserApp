package com.randhypi.githubuserapp.repository

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import com.randhypi.githubuserapp.data.UserSourceData
import com.randhypi.githubuserapp.data.UserTable
import com.randhypi.githubuserapp.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class  UserRepository(
    private val source: UserSourceData,
    private val myDispathcer: CoroutineDispatcher,
    private val contentResolver: ContentResolver,
    private val context: Context
) {

    companion object {

        fun insertUserFav(userItem: User, context: Context, contentResolver: ContentResolver) =
            runBlocking {
                withContext(Dispatchers.Default) {
                    val contentValues = ContentValues()
                    contentValues.put(UserTable.USERNAME, userItem.username)
                    contentValues.put(UserTable.NAME, userItem.name)
                    contentValues.put(UserTable.AVATAR, userItem.avatar)
                    contentResolver.insert(UserTable.CONTENT_URI, contentValues)
                }
            }

        fun delUserFav(contentResolver: ContentResolver,uri: Uri) = runBlocking {
            withContext(Dispatchers.Default) {
                contentResolver.delete(uri, null, null)
            }
        }
    }


    suspend fun getUserFavorite(): List<User>{
        return withContext(myDispathcer) {
             UserSourceData(contentResolver,context).getContentProviderValue()
        }
    }


}








