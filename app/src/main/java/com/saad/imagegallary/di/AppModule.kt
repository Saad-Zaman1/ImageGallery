package com.saad.imagegallary.di

import android.content.Context
import com.saad.imagegallary.utils.ApplicationContextProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplicationContextProvider(@ApplicationContext context: Context): ApplicationContextProvider {
        return ApplicationContextProvider(context)
    }
}