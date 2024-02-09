package com.sallyjayz.ranchid.recyclerview.report

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sallyjayz.ranchid.databinding.ReportItemListBinding
import com.sallyjayz.ranchid.model.report.keeper.Keeper

/**
 * Created by Salama Jatau on 14-Jul-23.
 */

class KeeperReportAdapter(private val listener: OnClickListener):
    PagingDataAdapter<Keeper,
            KeeperReportAdapter.KeeperReportViewHolder>(DiffUtilCallback()), Filterable {

    var keeperList = ArrayList<Keeper>()
    var keeperListFiltered = ArrayList<Keeper>()
//    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onItemClick(keeper: Keeper)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeeperReportViewHolder {
        val binding = ReportItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KeeperReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KeeperReportViewHolder, position: Int) {
        /*getItem(position)?.let {
            holder.bind(it)
        }*/

        val item = keeperList[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            listener.onItemClick(item)
        }
    }

    override fun getItemCount(): Int = keeperList.size

    inner class KeeperReportViewHolder(val binding: ReportItemListBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(allKeeper: Keeper) {
            binding.name.text = "${allKeeper.surname} ${allKeeper.other_names}"
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<Keeper>() {
        override fun areItemsTheSame(oldItem: Keeper, newItem: Keeper): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Keeper, newItem: Keeper): Boolean {
            return oldItem == newItem
        }

    }

    fun addData(list: ArrayList<Keeper>) {
        this.keeperList = list
        this.keeperListFiltered = list
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object:Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint == null || constraint.isEmpty()) {
                    filterResults.count = keeperListFiltered.size
                    filterResults.values = keeperListFiltered
                } else {
                    var searchChar: String = constraint.toString().lowercase()
                    var itemKeeper = ArrayList<Keeper>()
                    for (items in keeperListFiltered) {
                        if (items.surname.lowercase().contains(searchChar) || items.other_names.lowercase().contains(searchChar)) {
                            itemKeeper.add(items)
                        }
                    }
                    filterResults.count = itemKeeper.size
                    filterResults.values = itemKeeper
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                keeperList = results!!.values as ArrayList<Keeper>
                notifyDataSetChanged()
            }

        }
    }

    /*fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClickKeeper(position: Int, Keeper: Keepers)
    }*/

}