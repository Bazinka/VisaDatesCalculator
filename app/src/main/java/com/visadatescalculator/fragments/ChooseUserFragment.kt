package com.visadatescalculator.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.visadatescalculator.R
import com.visadatescalculator.viewmodel.ChooseUserViewModel
import com.visadatescalculator.viewmodel.ChooseUserViewModelFactory


class ChooseUserFragment : Fragment() {

    companion object {
        fun newInstance() = ChooseUserFragment()
    }

    private lateinit var viewModel: ChooseUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.choose_user_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, ChooseUserViewModelFactory(activity as Context))
            .get(ChooseUserViewModel::class.java)

        view?.findViewById<FloatingActionButton>(R.id.fab_add_person)?.setOnClickListener {
            view?.let { Navigation.findNavController(it).navigate(R.id.addPersonFragment) }
        }

        val recyclerView = view?.findViewById<RecyclerView>(R.id.persons_list)
        val adapter = PersonListAdapter(activity as Context, View.OnClickListener {
            val uid = it.tag.toString().toInt()
            view?.let {
                Navigation.findNavController(it).navigate(
                    R.id.datesCalculatorFragment,
                    bundleOf("uid" to uid)
                )
            }
        })
        recyclerView?.adapter = adapter

        viewModel.persons.observe(viewLifecycleOwner, Observer { persons ->
            if (!persons.isNullOrEmpty()) {
                adapter.setPersons(persons)
            }
        })
    }
}
