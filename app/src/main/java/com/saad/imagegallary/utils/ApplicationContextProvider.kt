package com.saad.imagegallary.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ApplicationContextProvider @Inject constructor(@ApplicationContext private val context: Context) {
    fun getApplicationContext(): Context {
        return context
    }
}