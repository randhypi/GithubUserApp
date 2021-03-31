package com.randhypi.githubuserapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.randhypi.githubuserapp.database.UserTable.Companion.TABLE_NAME


@Database(entities = arrayOf(UserTable::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object{
        private lateinit var INSTANCE: AppDatabase

        fun getInstance(context: Context?): AppDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(context!!, AppDatabase::class.java, TABLE_NAME)
                    .build()
            }
            return INSTANCE
        }
    }

    abstract fun getUserDao(): UserDao
}