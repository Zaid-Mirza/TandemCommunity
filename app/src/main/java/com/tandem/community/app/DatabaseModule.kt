package com.tandem.community.app

import android.content.Context
import androidx.room.Room
import com.tandem.community.data.local.dao.MembersDao
import com.tandem.community.data.local.db.TandemDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): TandemDatabase {
        return Room.databaseBuilder(
            context,
            TandemDatabase::class.java,
            "tandem_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideMembersDao(
        database: TandemDatabase
    ): MembersDao = database.membersDao()

}