package com.visadatescalculator.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
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
        return inflater.inflate(R.layout.add_person_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, AddPersonViewModelFactory(activity as Context))
            .get(AddPersonViewModel::class.java)
        view?.findViewById<MaterialButton>(R.id.button_save)?.setOnClickListener { button ->
            var name = view?.findViewById<TextInputLayout>(R.id.edit_person_name)?.editText?.text.toString()
            if (name.isEmpty()) {
                name = getString(R.string.empty_name_title)
            }
            viewModel.insertPerson(name)

            button.let {
                Navigation.findNavController(it).navigateUp()
            }
        }
    }

}
