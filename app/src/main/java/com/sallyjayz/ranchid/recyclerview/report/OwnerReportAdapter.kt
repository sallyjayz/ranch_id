package com.sallyjayz.ranchid.recyclerview.report

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sallyjayz.ranchid.databinding.ReportItemListBinding
import com.sallyjayz.ranchid.model.report.owner.Owner

/**
 * Created by Salama Jatau on 05-Aug-23.
 */
class OwnerReportAdapter(private val listener: OnClickListener): PagingDataAdapter<Owner,
        OwnerReportAdapter.OwnerReportViewHolder>(DiffUtilCallback()), Filterable {

    var ownerList = ArrayList<Owner>()
    var ownerListFiltered = ArrayList<Owner>()

    interface OnClickListener {
        fun onItemClick(owner: Owner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnerReportViewHolder {
        val binding = ReportItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OwnerReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OwnerReportViewHolder, position: Int) {
        val item = ownerList[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            listener.onItemClick(item)
        }
    }

    override fun getItemCount(): Int = ownerList.size

    inner class OwnerReportViewHolder(val binding: ReportItemListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(allOwner: Owner) {
            binding.name.text = "${allOwner.surname} ${allOwner.other_names}"
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<Owner>() {
        override fun areItemsTheSame(oldItem: Owner, newItem: Owner): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Owner, newItem: Owner): Boolean {
            return oldItem == newItem
        }

    }

    fun addData(list: ArrayList<Owner>) {
        this.ownerList = list
        this.ownerListFiltered = list
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object:Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint == null || constraint.isEmpty()) {
                    filterResults.count = ownerListFiltered.size
                    filterResults.values = ownerListFiltered
                } else {
                    var searchChar: String = constraint.toString().lowercase()
                    var itemOwner = ArrayList<Owner>()
                    for (items in ownerListFiltered) {
                        if (items.surname.lowercase().contains(searchChar) || items.other_names.lowercase().contains(searchChar)) {
                            itemOwner.add(items)
                        }
                    }
                    filterResults.count = itemOwner.size
                    filterResults.values = itemOwner
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                ownerList = results!!.values as ArrayList<Owner>
                notifyDataSetChanged()
            }

        }
    }
}