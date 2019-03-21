package com.visadatescalculator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.visadatescalculator.model.Person
import com.visadatescalculator.model.PersonDao
import com.visadatescalculator.model.Trip
import com.visadatescalculator.model.TripDao


@Database(entities = [Person::class, Trip::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class MainDatabase : RoomDatabase() {

    abstract fun personDao(): PersonDao

    abstract fun tripDao(): TripDao

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