package com.sisnperu.ccollachea.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sisnperu.ccollachea.database.dao.PersonaDao
import com.sisnperu.ccollachea.database.entities.PersonaEntity
import com.sisnperu.ccollachea.model.Persona

@Database(entities = [PersonaEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun personaDao(): PersonaDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "sisnperu")
                .build()
        }
    }
}
