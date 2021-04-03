package com.randhypi.githubuserapp.provider

import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.randhypi.githubuserapp.data.AppDatabase
import com.randhypi.githubuserapp.data.UserDao
import com.randhypi.githubuserapp.data.UserTable.Companion.AUTHORITY
import com.randhypi.githubuserapp.data.UserTable.Companion.AVATAR
import com.randhypi.githubuserapp.data.UserTable.Companion.NAME
import com.randhypi.githubuserapp.data.UserTable.Companion.TABLE_NAME
import com.randhypi.githubuserapp.data.UserTable.Companion.USERNAME
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UserProviderTest{
    companion object{
        val TAG: String = UserProvider::class.java.simpleName
        val USER_URI: Uri = Uri.parse("content://" + AUTHORITY.toString() + "/" + TABLE_NAME)
        val USER_URI_ID: Uri =
            Uri.parse("content://" + AUTHORITY.toString() + "/" + TABLE_NAME.toString() + "/" + "1")
        lateinit var contentResolver: ContentResolver
    }
    private lateinit var userDao: UserDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        contentResolver = InstrumentationRegistry.getInstrumentation().context.contentResolver
    }


    @Test
    fun insertUserDataTest() {
        val insertUri = contentResolver.insert(USER_URI, contentValues())
        assertNotNull(insertUri)
    }

    @Test
    fun queryPerson() {
        val cursor: Cursor? = contentResolver.query(
            USER_URI, null, null,
            null, null
        )
        Log.d(TAG,cursor.toString())
        assertNotNull(cursor)

        if (cursor?.moveToFirst() == true) {
            do {

                val username: String = cursor.getString(cursor.getColumnIndex(USERNAME))
                val name: String = cursor.getString(cursor.getColumnIndex(NAME))
                val avatar: String =
                    cursor.getString(cursor.getColumnIndex(AVATAR))
            } while (cursor.moveToNext())
        }
        cursor?.close()
    }

    @Test
    fun delete() {
        val count = contentResolver.delete(USER_URI_ID, null, null)
        assertNotNull(count);
    }

    private fun contentValues(): ContentValues {
        val contentValues: ContentValues = ContentValues()
        contentValues.put(USERNAME, "randhypiP")
        contentValues.put(NAME, "randhy")
        contentValues.put(AVATAR, "randhy/randhy.jpg")
        return contentValues;
    }
}
