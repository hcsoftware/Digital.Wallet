package com.xr6software.digitalwallet.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.xr6software.digitalwallet.model.User

/**
 * @author Hernán Carrera
 * @version 1.0
 * Room Database class with singleton Imp.
 */
@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: UserDatabase? = null
        fun getDatabase(context: Context): UserDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context, UserDatabase::class.java, "user_database")
                            .build()
                }
            }
            return INSTANCE!!
        }
    }

}