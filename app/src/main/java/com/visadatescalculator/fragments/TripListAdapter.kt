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
import com.visadatescalculator.model.Trip
import java.text.SimpleDateFormat


class TripListAdapter internal constructor(
    context: Context, @Nullable private val clickCallback: View.OnClickListener
) : ListAdapter<Trip, TripListAdapter.TripListViewHolder>(TripDiffCallback()) {
    private var trips: List<Trip> = emptyList()
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    inner class TripListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tripDateFromTextView: TextView = itemView.findViewById(R.id.trip_date_from)
        val tripDateToTextView: TextView = itemView.findViewById(R.id.trip_date_to)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripListViewHolder {
        val itemView = inflater.inflate(R.layout.trip_item, parent, false)
        return TripListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TripListViewHolder, position: Int) {
        val current = trips[position]

        val format = SimpleDateFormat("dd.mm.yyyy")

        holder.tripDateFromTextView.text = format.format(current.enterDate)
        holder.tripDateToTextView.text = format.format(current.leaveDate)
//        holder.apply {
//            itemView.tag = current.uid
//            itemView.setOnClickListener {
//                clickCallback.onClick(it)
//            }
//        }
    }

    internal fun setTrips(words: List<Trip>) {
        this.trips = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = trips.size
}


private class TripDiffCallback : DiffUtil.ItemCallback<Trip>() {

    override fun areItemsTheSame(oldItem: Trip, newItem: Trip): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: Trip, newItem: Trip): Boolean {
        return oldItem == newItem
    }
}