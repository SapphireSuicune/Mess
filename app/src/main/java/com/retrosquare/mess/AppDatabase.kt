package com.retrosquare.mess

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Mess::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun messDao(): MessDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mess_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}