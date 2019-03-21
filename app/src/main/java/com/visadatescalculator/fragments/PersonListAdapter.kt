package com.visadatescalculator.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.visadatescalculator.R
import com.visadatescalculator.model.Person

class PersonListAdapter internal constructor(
    context: Context, @Nullable private val clickCallback: View.OnClickListener
) : ListAdapter<Person, PersonListAdapter.PersonListViewHolder>(PersonDiffCallback()) {
    private var persons: List<Person> = emptyList()
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    // Cached copy of persons

    inner class PersonListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val personNameItemView: TextView = itemView.findViewById(R.id.person_item_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonListViewHolder {
        val itemView = inflater.inflate(R.layout.person_item, parent, false)
        return PersonListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PersonListViewHolder, position: Int) {
        val current = persons[position]
        holder.personNameItemView.text = current.name
        holder.apply {
            itemView.tag = current.uid
            itemView.setOnClickListener {
                clickCallback.onClick(it)
            }
        }
    }

    internal fun setPersons(words: List<Person>) {
        this.persons = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = persons.size
}


private class PersonDiffCallback : DiffUtil.ItemCallback<Person>() {

    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }
}