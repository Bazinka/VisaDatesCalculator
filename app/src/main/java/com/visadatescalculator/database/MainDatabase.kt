package com.visadatescalculator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.visadatescalculator.model.Person
import com.visadatescalculator.model.PersonDao


@Database(entities = [Person::class], version = 1, exportSchema = false)
abstract class MainDatabase : RoomDatabase() {

    abstract fun personDao(): PersonDao

    companion object {
        @Volatile
        private var instance: MainDatabase? = null

        fun getDatabase(context: Context): MainDatabase {
            val tempInstance = instance
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val tempInstance = Room.databaseBuilder(
                    context,
                    MainDatabase::class.java,
                    "dates_calculator_database"
                ).build()
                instance = tempInstance
                return tempInstance
            }
        }

    }
}