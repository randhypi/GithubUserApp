package com.randhypi.githubuserapp.database

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.randhypi.githubuserapp.database.UserTable.Companion.TABLE_NAME

@Dao
interface UserDao {

    @Query("SELECT * FROM userTable")
    fun getAll(): Cursor

    @Insert
    fun insert(user: UserTable?): Long

    @Query("DELETE FROM $TABLE_NAME WHERE  id = :id")
    fun delete(id: Long): Int
}