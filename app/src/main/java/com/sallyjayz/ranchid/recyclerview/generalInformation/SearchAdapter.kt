package com.sallyjayz.ranchid.recyclerview.generalInformation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.model.generalInformation.search.SearchInformation

/**
 * Created by Salama Jatau on 06-Jul-23.
 */
class SearchAdapter(var searchList: List<SearchInformation>) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClickSearchInformation(position: Int, searchInformation: SearchInformation)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.search_dropdown_list_item, parent, false)
        return SearchViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = searchList[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {

            if (onClickListener != null) {
                onClickListener!!.onClickSearchInformation(
                    position,
                    item
                )
            }

            /*item.let { searchInformation ->
                onClickListener!!.onClickSearchInformation(
                    position,
                    searchInformation
                )
            }*/
        }
    }

    override fun getItemCount(): Int = searchList.size

    class SearchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val searchName: MaterialTextView = itemView.findViewById(R.id.search_name)

        fun bind(searchInformation: SearchInformation){
            searchName.text = "${searchInformation.surname} ${searchInformation.other_names}"
        }

    }
}