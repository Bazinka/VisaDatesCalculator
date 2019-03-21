package com.visadatescalculator.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.visadatescalculator.DataRepository
import com.visadatescalculator.model.Trip


class DaysCalculatorViewModel(context: Context, personId: Int) : ViewModel() {

    private var repository: DataRepository = DataRepository(context)

    val trips: LiveData<List<Trip>> = repository.getTripsByPersonId(personId)

    val result: Boolean? = null

}