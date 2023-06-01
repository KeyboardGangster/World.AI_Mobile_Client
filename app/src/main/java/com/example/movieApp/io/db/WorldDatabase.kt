package com.example.movieApp.io.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieApp.models.Movie
import com.example.movieApp.models.World
import com.example.movieApp.utils.CustomConverters

@Database (
    entities = [World::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CustomConverters::class)
abstract class WorldDatabase: RoomDatabase() {

    abstract fun worldDao(): WorldDao

    companion object {
        @Volatile
        private var Instance: WorldDatabase?=null

        fun getDatabase(context: Context): WorldDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, WorldDatabase::class.java, "world_db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        Instance = it
                    }
            }
        }
    }
}