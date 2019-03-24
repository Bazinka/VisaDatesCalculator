package com.visadatescalculator.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TripDao {
    @Query("SELECT * FROM trip")
    fun getAll(): LiveData<List<Trip>>

    @Query("SELECT * FROM trip where person_uid = :personId ORDER BY leave_date DESC")
    fun getTripsByPersonId(personId: Int): LiveData<List<Trip>>


    @Insert
    fun insertAll(vararg trips: Trip)

    @Insert
    fun insertTrip(trip: Trip)

    @Delete
    fun delete(trip: Trip)
}