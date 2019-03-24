package com.visadatescalculator.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.visadatescalculator.DataRepository
import com.visadatescalculator.model.Trip
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import java.util.*


class AddTripViewModel(context: Context, val personId: Int) : ViewModel() {

    private var repository: DataRepository = DataRepository(context)

    val intersectionTrips: MutableLiveData<List<Trip>> by lazy {
        MutableLiveData<List<Trip>>()
    }

    val allTrips: LiveData<List<Trip>> = repository.getTripsByPersonId(personId)

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }


    fun insertTrip(viewLifecycleOwner: LifecycleOwner, enterDate: Date, leaveDate: Date) {
        allTrips.observe(viewLifecycleOwner, Observer<List<Trip>> {
            allTrips.removeObservers(viewLifecycleOwner)
            val listTrips = checkTripPeriods(
                DateTime(enterDate),
                DateTime(leaveDate)
            )
            if (listTrips.isEmpty()) {
                viewModelScope.launch {
                    repository.insertTrip(Trip(DateTime(enterDate), DateTime(leaveDate), personId))
                }
            }
            intersectionTrips.value = listTrips
        })
    }

    fun checkTripPeriods(startDate: DateTime, endDate: DateTime): List<Trip> {
        val intersectionTripList = mutableListOf<Trip>()
        for (trip in allTrips.value ?: emptyList()) {
            if ((startDate.isBefore(trip.enterDate) && endDate.isBefore(trip.enterDate)) ||
                (startDate.isAfter(trip.leaveDate) && endDate.isAfter(trip.leaveDate))
            ) {
                Log.i("Check peroid", "That is good period")
            } else {
                intersectionTripList.add(trip)
            }
        }
        return intersectionTripList
    }
}
