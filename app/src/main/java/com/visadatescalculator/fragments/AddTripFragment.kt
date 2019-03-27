package com.visadatescalculator.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.visadatescalculator.R
import com.visadatescalculator.model.Trip
import com.visadatescalculator.viewmodel.AddTripViewModel
import com.visadatescalculator.viewmodel.AddTripViewModelFactory
import org.joda.time.DateTime


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

        view?.findViewById<MaterialButton>(R.id.button_save)?.setOnClickListener { button ->
            val enterDate = view?.findViewById<TextInputEditText>(R.id.enter_date_text)?.tag as DateTime
            val leaveDate = view?.findViewById<TextInputEditText>(R.id.leave_date_text)?.tag as DateTime
            viewModel.insertTrip(viewLifecycleOwner, enterDate, leaveDate)
        }

        view?.findViewById<TextInputEditText>(R.id.enter_date_text)?.setOnFocusChangeListener { editView, focus ->
            if (focus) {
                (editView as EditText).showSoftInputOnFocus = false
                view?.findViewById<View>(R.id.enter_date_picker)?.let { showView(it) }
                view?.findViewById<View>(R.id.leave_date_picker)?.let { hideView(it) }
            }
        }

        view?.findViewById<TextInputEditText>(R.id.leave_date_text)?.setOnFocusChangeListener { editView, focus ->
            if (focus) {
                (editView as EditText).showSoftInputOnFocus = false
                view?.findViewById<View>(R.id.leave_date_picker)?.let { showView(it) }
                view?.findViewById<View>(R.id.enter_date_picker)?.let { hideView(it) }
            }
        }

        viewModel.intersectionTrips.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                view?.findViewById<MaterialButton>(R.id.button_save)?.let {
                    Navigation.findNavController(it).navigateUp()
                }
            } else {
                showErrorDialog(it)
            }
        })

        view?.findViewById<DatePicker>(R.id.enter_date_picker)
            ?.setOnDateChangedListener { datePicker, year, month, day ->
                view?.findViewById<TextInputEditText>(R.id.enter_date_text)
                    ?.let { setUpDatePicker(year, month + 1, day, it) }
                hideView(datePicker)
                view?.findViewById<TextInputEditText>(R.id.leave_date_text)?.requestFocus()
            }
        view?.findViewById<DatePicker>(R.id.leave_date_picker)
            ?.setOnDateChangedListener { datePicker, year, month, day ->
                view?.findViewById<TextInputEditText>(R.id.leave_date_text)
                    ?.let { setUpDatePicker(year, month + 1, day, it) }
            }
    }

    private fun setUpDatePicker(year: Int, month: Int, day: Int, enterDateTextView: TextInputEditText) {
        val newDate = DateTime(year, month + 1, day, 0, 0, 0)
        enterDateTextView.setText(newDate.toString("dd MMM yyyy"))
        enterDateTextView.tag = newDate
    }

    private fun hideView(myView: View) {
        val cx = myView.width.div(2)
        val cy = myView.height.div(2)

        val initialRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

        val anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0f)

        anim.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                myView.visibility = View.GONE
            }
        })

        anim.start()
    }

    private fun showView(myView: View) {
        val cx = myView.width.div(2)
        val cy = myView.height.div(2)

        val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

        val anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0f, finalRadius)
        myView.visibility = View.VISIBLE
        anim.start()
    }

    private fun showErrorDialog(list: List<Trip>?) {
        val builder = AlertDialog.Builder(activity as Context);
        builder.setTitle("Ошибка")
        builder.setMessage("Нельзя создать поездку, даты пересекаются с текущими поездками")
        builder.setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.dismiss()
        })
        builder.create().show();
    }
}
