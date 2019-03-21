package com.visadatescalculator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.visadatescalculator.R
import com.visadatescalculator.viewmodel.DatesCalculatorViewModel


class DatesCalculatorFragment : Fragment() {

    companion object {
        fun newInstance() = DatesCalculatorFragment()
    }

    private lateinit var viewModel: DatesCalculatorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dates_calculator_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DatesCalculatorViewModel::class.java)
        view?.findViewById<TextView>(R.id.id_text)?.text = "uid = " + arguments?.getInt("uid")

    }

}
