package com.randhypi.githubuserapp.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.randhypi.githubuserapp.data.AppDatabase
import com.randhypi.githubuserapp.data.UserDao
import com.randhypi.githubuserapp.data.UserTable
import com.randhypi.githubuserapp.data.UserTable.Companion.AUTHORITY
import com.randhypi.githubuserapp.data.UserTable.Companion.CONTENT_URI
import com.randhypi.githubuserapp.data.UserTable.Companion.TABLE_NAME


class UserProvider : ContentProvider() {

    companion object{
        var TAG = UserProvider::class.java.simpleName
        private lateinit var userDao: UserDao
        private const val USER = 1
        private const val USER_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            // content://com.dicoding.picodiploma.mynotesapp/note
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, USER)
            // content://com.dicoding.picodiploma.mynotesapp/note/id
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", USER_ID)
        }
    }

    lateinit var lifecycleOwner: LifecycleOwner



    override fun onCreate(): Boolean {
        userDao = context?.let { AppDatabase.getInstance(it).getUserDao() }!!
        return true;
    }


    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        Log.d(TAG, "query")
        val cursor: Cursor
        when (sUriMatcher.match(uri)) {
            USER -> {

           cursor = userDao.getAll()

                if (context != null) {
                    cursor.setNotificationUri(
                        context?.getContentResolver(), uri
                    )
                    return cursor
                }
                throw IllegalArgumentException("Unknown URI: $uri")
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d(TAG, "insert")
        when (sUriMatcher.match(uri)) {
            USER -> {
                if (context != null) {
                    val id: Long = userDao.insert(values?.let { UserTable.fromContentValues(it) })
                    if (id != 0L) {
                        context!!.contentResolver.notifyChange(CONTENT_URI, null)
                        return Uri.parse("$CONTENT_URI/$id")
                    }
                }
                throw java.lang.IllegalArgumentException("Invalid URI: Insert failed$uri")
            }
            USER_ID -> throw java.lang.IllegalArgumentException("Invalid URI: Insert failed$uri")
            else -> throw java.lang.IllegalArgumentException("Unknown URI: $uri")
        }
    }


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        Log.d(TAG, "delete")
        when (sUriMatcher.match(uri)) {
            USER -> throw java.lang.IllegalArgumentException("Invalid uri: cannot delete")
            USER_ID -> {
                if (context != null) {
                    val count: Int = userDao.delete(ContentUris.parseId(uri))
                    context!!.contentResolver.notifyChange(uri, null)
                    return count
                }
                throw java.lang.IllegalArgumentException("Unknown URI:$uri")
            }
            else -> throw java.lang.IllegalArgumentException("Unknown URI:$uri")
        }
    }

    override fun getType(uri: Uri): String? {
      return null
    }


    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}