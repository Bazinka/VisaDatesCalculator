package com.visadatescalculator

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.visadatescalculator.database.MainDatabase
import com.visadatescalculator.model.Person
import com.visadatescalculator.model.Trip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataRepository internal constructor(private val context: Context) {

    @WorkerThread
    suspend fun insertPerson(person: Person) {
        withContext(Dispatchers.IO) {
            MainDatabase.getDatabase(context).personDao().insertPerson(person)
        }
    }

    fun getAllPersons(): LiveData<List<Person>> {
        return MainDatabase.getDatabase(context).personDao().getAll()
    }

    @WorkerThread
    suspend fun insertTrip(trip: Trip) {
        withContext(Dispatchers.IO) {
            MainDatabase.getDatabase(context).tripDao().insertTrip(trip)
        }
    }

    @WorkerThread
    suspend fun deleteTrip(trip: Trip) {
        withContext(Dispatchers.IO) {
            MainDatabase.getDatabase(context).tripDao().delete(trip)
        }
    }

    fun getAllTrips(): LiveData<List<Trip>> {
        return MainDatabase.getDatabase(context).tripDao().getAll()
    }

    fun getTripsByPersonId(personId: Int): LiveData<List<Trip>> {
        return MainDatabase.getDatabase(context).tripDao().getTripsByPersonId(personId)
    }

    companion object {
        @Volatile
        private var instance: DataRepository? = null

        fun getDataRepository(context: Context): DataRepository {
            val tempInstance = instance
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val tempInstance = DataRepository(context)
                instance = tempInstance
                return tempInstance
            }
        }
    }
}