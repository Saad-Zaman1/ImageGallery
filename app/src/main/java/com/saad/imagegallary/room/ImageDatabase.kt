package com.saad.imagegallary.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saad.imagegallary.models.Hit

@Database(entities = [Hit::class, FavoriteEntity::class], version = 1, exportSchema = false)
abstract class ImageDatabase : RoomDatabase() {

    abstract fun imageDao(): ImagesDao
    abstract fun favoriteDao(): FavoriteDao

//    companion object {
//        @Volatile
//        private var INSTANCE: ImageDatabase? = null
//        fun getDatabase(context: Context): ImageDatabase {
//            if (INSTANCE == null) {
//                synchronized(this) {
//                    INSTANCE = Room.databaseBuilder(
//                        context,
//                        ImageDatabase::class.java,
//                        "imageDB"
//                    )
//                        .build()
//                }
//            }
//            return INSTANCE!!
//        }
//
//    }

}