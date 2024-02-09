package com.sallyjayz.ranchid.recyclerview.report

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sallyjayz.ranchid.databinding.ReportItemListBinding
import com.sallyjayz.ranchid.model.report.location.Location

/**
 * Created by Salama Jatau on 09-Aug-23.
 */
class LocationReportAdapter(private val listener: OnClickListener):
    PagingDataAdapter<Location, LocationReportAdapter.LocationReportViewHolder>(DiffUtilCallback()),
    Filterable {

    var locationList = ArrayList<Location>()
    var locationListFiltered = ArrayList<Location>()

    interface OnClickListener {
        fun onItemClick(location: Location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationReportViewHolder {
        val binding = ReportItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationReportViewHolder, position: Int) {
        val item = locationList[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            listener.onItemClick(item)
        }
    }

    override fun getItemCount(): Int = locationList.size

    class LocationReportViewHolder(val binding: ReportItemListBinding):
        RecyclerView.ViewHolder(binding.root)  {

        fun bind(location: Location) {
            binding.name.text = "${location.location_name}"
        }

    }


    class DiffUtilCallback : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem == newItem
        }

    }

    fun addData(list: ArrayList<Location>) {
        this.locationList = list
        this.locationListFiltered = list
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object:Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint == null || constraint.isEmpty()) {
                    filterResults.count = locationListFiltered.size
                    filterResults.values = locationListFiltered
                } else {
                    var searchChar: String = constraint.toString().lowercase()
                    var itemLocation = ArrayList<Location>()
                    for (items in locationListFiltered) {
                        if (items.location_name.lowercase().contains(searchChar)) {
                            itemLocation.add(items)
                        }
                    }
                    filterResults.count = itemLocation.size
                    filterResults.values = itemLocation
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                locationList = results!!.values as ArrayList<Location>
                notifyDataSetChanged()
            }

        }
    }
}