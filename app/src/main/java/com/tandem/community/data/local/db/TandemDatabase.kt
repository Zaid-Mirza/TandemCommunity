package com.tandem.community.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tandem.community.data.local.dao.MembersDao
import com.tandem.community.data.local.entities.MembersEntity

@Database(
    entities = [MembersEntity::class],
    version = 1,
    exportSchema = true
)
abstract class TandemDatabase : RoomDatabase() {

    abstract fun membersDao(): MembersDao


    companion object {
        @Volatile
        private var INSTANCE: TandemDatabase? = null

        fun getInstance(context: Context): TandemDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TandemDatabase::class.java,
                    "tandem_db"
                )
                    .fallbackToDestructiveMigration() // We don't add this in production, but it is fine for this take home assignment
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}