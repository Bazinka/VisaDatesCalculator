package com.visadatescalculator.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.visadatescalculator.R
import com.visadatescalculator.viewmodel.DaysCalculatorViewModel
import com.visadatescalculator.viewmodel.DaysCalculatorViewModelFactory


class DaysCalculatorFragment : Fragment() {

    companion object {
        fun newInstance() = DaysCalculatorFragment()
    }

    private lateinit var viewModel: DaysCalculatorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val personUid = arguments?.getInt("uid") ?: -1
        if (personUid < 0) {
//            Navigation.findNavController(view).navigateUp()
        }

        viewModel = ViewModelProviders.of(
            this,
            DaysCalculatorViewModelFactory(activity as Context, personUid)
        )
            .get(DaysCalculatorViewModel::class.java)

        return inflater.inflate(R.layout.days_calculator_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        view?.findViewById<FloatingActionButton>(R.id.fab_add_trip)?.setOnClickListener {
            view?.let { Navigation.findNavController(it).navigate(R.id.addTripFragment) }
        }

        val recyclerView = view?.findViewById<RecyclerView>(R.id.trip_list)
        val adapter = TripListAdapter(activity as Context)
        recyclerView?.adapter = adapter

        viewModel.trips.observe(viewLifecycleOwner, Observer { trips ->
            val emptyTextView = view?.findViewById<TextView>(R.id.trip_empty_text)
            if (!trips.isNullOrEmpty()) {
                adapter.setTrips(trips)
                emptyTextView?.visibility = GONE
            } else {
                emptyTextView?.visibility = VISIBLE
            }
        })
    }

}
