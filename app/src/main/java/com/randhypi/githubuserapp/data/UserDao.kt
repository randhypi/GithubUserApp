package com.randhypi.githubuserapp.data

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.randhypi.githubuserapp.data.UserTable.Companion.TABLE_NAME

@Dao
interface UserDao {

    @Query("SELECT * FROM userTable")
    fun getAll(): Cursor

    @Insert
    fun insert(user: UserTable?): Long

    @Query("SELECT COUNT() FROM userTable WHERE id = :id")
    fun count(id: Long): Cursor

    @Query("DELETE FROM $TABLE_NAME WHERE  id = :id")
    fun delete(id: Long): Int
}