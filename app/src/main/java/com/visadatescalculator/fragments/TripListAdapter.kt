package com.visadatescalculator.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.visadatescalculator.R
import com.visadatescalculator.model.Trip
import org.joda.time.Days


class TripListAdapter internal constructor(
    context: Context, val clickListener: View.OnClickListener
) : ListAdapter<Trip, TripListAdapter.TripListViewHolder>(TripDiffCallback()) {
    private var trips: List<Trip> = emptyList()
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    inner class TripListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tripDateFromTextView: TextView = itemView.findViewById(R.id.trip_date_from)
        val tripDateToTextView: TextView = itemView.findViewById(R.id.trip_date_to)
        val tripDaysAmountTextView: TextView = itemView.findViewById(R.id.trip_days_amount)
        val tripDeleteImageButton: ImageButton = itemView.findViewById(R.id.trip_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripListViewHolder {
        val itemView = inflater.inflate(R.layout.trip_item, parent, false)
        return TripListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TripListViewHolder, position: Int) {
        val current = trips[position]

        holder.tripDateFromTextView.text = current.enterDate.toString("dd MMM yy")
        holder.tripDateToTextView.text = current.leaveDate.toString("dd MMM yy")
        val daysAmount = Days.daysBetween(
            current.enterDate.withTimeAtStartOfDay(),
            current.leaveDate.withTimeAtStartOfDay()
        ) + 1
        holder.tripDaysAmountTextView.text = daysAmount.days.toString()
        holder.tripDeleteImageButton.apply {
            tag = current
            setOnClickListener {
                clickListener.onClick(it)
            }
        }
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