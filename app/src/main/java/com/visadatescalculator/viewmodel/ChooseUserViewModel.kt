package com.visadatescalculator.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.visadatescalculator.DataRepository
import com.visadatescalculator.model.Person


class ChooseUserViewModel(context: Context) : ViewModel() {

    private var repository: DataRepository = DataRepository(context)

    val persons: LiveData<List<Person>> = repository.getAllPersons()


}
