package com.visadatescalculator.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class DaysCalculatorViewModelFactory(
    private val context: Context,
    private val personId: Int
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = DaysCalculatorViewModel(context, personId) as T
}