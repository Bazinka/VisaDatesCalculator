package com.visadatescalculator.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PersonDao {
    @Query("SELECT * FROM person")
    fun getAll(): LiveData<List<Person>>

    @Insert
    fun insertAll(vararg users: Person)

    @Insert
    fun insertPerson(user: Person)

    @Delete
    fun delete(user: Person)
}