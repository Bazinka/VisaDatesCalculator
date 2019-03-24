package com.visadatescalculator.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.visadatescalculator.DataRepository
import com.visadatescalculator.model.Trip
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import java.util.*


class AddTripViewModel(context: Context, val personId: Int) : ViewModel() {

    private var repository: DataRepository = DataRepository(context)

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }


    fun insertTrip(enterDate: Date, leaveDate: Date) {
        viewModelScope.launch {
            repository.insertTrip(Trip(DateTime(enterDate), DateTime(leaveDate), personId))
        }
    }

}
