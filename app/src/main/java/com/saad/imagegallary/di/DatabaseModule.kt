package com.saad.imagegallary.di

import android.content.Context
import androidx.room.Room
import com.saad.imagegallary.room.FavoriteDao
import com.saad.imagegallary.room.ImageDatabase
import com.saad.imagegallary.room.ImagesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Singleton
    @Provides
    fun providesImageDao(database: ImageDatabase): ImagesDao {
        return database.imageDao()
    }

    @Singleton
    @Provides
    fun provideFavoriteDao(database: ImageDatabase): FavoriteDao {
        return database.favoriteDao()
    }

    @Singleton
    @Provides
    fun provideRoomDb(
        @ApplicationContext appContext: Context
    ): ImageDatabase {
        return Room.databaseBuilder(
            appContext,
            ImageDatabase::class.java,
            "imageDB"
        )
            .build()
    }
}