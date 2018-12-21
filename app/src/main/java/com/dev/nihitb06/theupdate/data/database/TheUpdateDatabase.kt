package com.dev.nihitb06.theupdate.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class TheUpdateDatabase : RoomDatabase () {

    abstract fun articleDao(): ArticleDao

    companion object {

        private const val DATABASE_NAME = "TheUpdateDatabase"

        @Volatile
        private var INSTANCE: TheUpdateDatabase? = null

        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                TheUpdateDatabase::class.java,
                DATABASE_NAME
        ).build()
    }
}