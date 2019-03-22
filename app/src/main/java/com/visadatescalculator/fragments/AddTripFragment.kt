package com.visadatescalculator.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.visadatescalculator.R
import com.visadatescalculator.viewmodel.AddTripViewModel
import com.visadatescalculator.viewmodel.AddTripViewModelFactory
import java.text.SimpleDateFormat
import java.util.*


class AddTripFragment : Fragment() {

    companion object {
        fun newInstance() = AddTripFragment()
    }

    private lateinit var viewModel: AddTripViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_trip_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val personUid = AddTripFragmentArgs
            .fromBundle(arguments ?: Bundle())
            .personUid

        viewModel = ViewModelProviders.of(this, AddTripViewModelFactory(activity as Context, personUid))
            .get(AddTripViewModel::class.java)

        view?.findViewById<Button>(R.id.button_save)?.setOnClickListener { button ->
            val enterDate = view?.findViewById<TextView>(R.id.enter_date_text)?.tag as Date
            val leaveDate = view?.findViewById<TextView>(R.id.leave_date_text)?.tag as Date
            viewModel.insertTrip(enterDate, leaveDate)

            button.let {
                Navigation.findNavController(it).navigateUp()
            }
        }

        setUpDatePicker(R.id.choose_enter_date_layout, R.id.enter_date_text)
        setUpDatePicker(R.id.choose_leave_date_layout, R.id.leave_date_text)
    }

    private fun setUpDatePicker(layoutId: Int, textViewId: Int) {
        val formatter = SimpleDateFormat("dd.MM.yyyy")
        view?.findViewById<LinearLayout>(layoutId)?.setOnClickListener {
            val newCalendar = Calendar.getInstance();
            DatePickerDialog(
                activity as Context,
                DatePickerDialog.OnDateSetListener() { datePicker: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    val newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    val enterDateTextView = view?.findViewById<TextView>(textViewId)
                    enterDateTextView?.text = formatter.format(newDate.time)
                    enterDateTextView?.tag = newDate.time
                },
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

}
