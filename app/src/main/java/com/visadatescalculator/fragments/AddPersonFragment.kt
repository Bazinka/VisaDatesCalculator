package com.visadatescalculator.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.visadatescalculator.R
import com.visadatescalculator.viewmodel.AddPersonViewModel
import com.visadatescalculator.viewmodel.AddPersonViewModelFactory


class AddPersonFragment : Fragment() {

    companion object {
        fun newInstance() = AddPersonFragment()
    }

    private lateinit var viewModel: AddPersonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.add_person_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, AddPersonViewModelFactory(activity as Context))
            .get(AddPersonViewModel::class.java)
        view?.findViewById<Button>(R.id.button_save)?.setOnClickListener { button ->
            var name = view?.findViewById<EditText>(R.id.edit_person_name)?.text.toString()
            if (name.isEmpty()) {
                name = getString(R.string.empty_name_title)
            }
            viewModel.insertPerson(name)

            button.let {
                Navigation.findNavController(it).navigate(R.id.action_addPersonFragment_to_chooseUserFragment)
            }
        }
    }

}
