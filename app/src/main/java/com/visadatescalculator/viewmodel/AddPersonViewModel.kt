package com.visadatescalculator.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.visadatescalculator.DataRepository
import com.visadatescalculator.model.Person
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class AddPersonViewModel(context: Context) : ViewModel() {

    private var repository: DataRepository = DataRepository(context)

    private val persons: LiveData<List<Person>> = repository.getAllPersons()

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }


     fun insertPerson(name: String) {
        viewModelScope.launch {
            repository.insertPerson(Person(name = name))
        }
    }

}
