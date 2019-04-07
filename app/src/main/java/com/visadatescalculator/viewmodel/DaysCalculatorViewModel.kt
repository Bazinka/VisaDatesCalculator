package com.visadatescalculator.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.visadatescalculator.DataRepository
import com.visadatescalculator.model.Trip
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.Period


class DaysCalculatorViewModel(context: Context, val personId: Int) : ViewModel() {

    private var repository: DataRepository = DataRepository(context)

    val trips: LiveData<List<Trip>> = repository.getTripsByPersonId(personId)

    val result: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    fun calculateVisaDays() {
        val tripList: List<Trip> = trips.value ?: emptyList()
        var daysAmount = 0
        if (!tripList.isEmpty()) {
            val lastDate = tripList[0].leaveDate
            val lastDateTime = DateTime(lastDate)
            val minusHalfYearPeriod = lastDateTime.minus(Period.days(180))

            for (trip in tripList) {
                if (trip.leaveDate.isAfter(minusHalfYearPeriod)) {
                    val daysInTrip: Int = if (trip.enterDate.isAfter(minusHalfYearPeriod)) {
                        Days.daysBetween(
                            trip.enterDate.withTimeAtStartOfDay(),
                            trip.leaveDate.withTimeAtStartOfDay()
                        ).days + 1
                    } else {
                        Days.daysBetween(
                            trip.enterDate.withTimeAtStartOfDay(),
                            minusHalfYearPeriod.withTimeAtStartOfDay()
                        ).days + 1
                    }
                    daysAmount += daysInTrip
                }
            }
        }
        result.value = daysAmount
    }

    fun deleteTrip(trip: Trip) {
        viewModelScope.launch {
            repository.deleteTrip(trip)
        }
    }
}