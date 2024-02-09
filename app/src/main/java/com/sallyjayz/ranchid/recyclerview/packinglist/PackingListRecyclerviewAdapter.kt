package com.sallyjayz.ranchid.recyclerview.packinglist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.model.register.packinglist.scannedlivestock.ScanLivestock

/**
 * Created by Salama Jatau on 19-Jun-23.
 */

class PackingListRecyclerviewAdapter(private val context:Context): PagingDataAdapter<ScanLivestock,
        PackingListRecyclerviewAdapter.PackingListViewHolder>(DiffUtilCallback()) {

    private var onClickListener: OnClickListener? = null

    interface OnClickListener {
        fun onClickLivestock(position: Int, scanLivestock: ScanLivestock)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackingListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.packing_list_item, parent, false)
        return PackingListViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: PackingListViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)

            holder.itemView.setOnClickListener {
                if (onClickListener != null) {
                    getItem(position)?.let { scanLivestock ->
                        onClickListener!!.onClickLivestock(
                            position,
                            scanLivestock
                        )
                    }
                }
            }
        }

        holder.tagListNumbering.text = position.plus(1).toString()

    }

    class PackingListViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val tagId: MaterialTextView = view.findViewById(R.id.scan_tag)
        val tagListNumbering: MaterialTextView = view.findViewById(R.id.scan_number)

        fun bind(scanLivestock: ScanLivestock) {
            tagId.text = scanLivestock.tag_id
        }

    }

    class DiffUtilCallback : DiffUtil.ItemCallback<ScanLivestock>() {
        override fun areItemsTheSame(oldItem: ScanLivestock, newItem: ScanLivestock): Boolean {
            return oldItem.tag_id == newItem.tag_id
        }

        override fun areContentsTheSame(oldItem: ScanLivestock, newItem: ScanLivestock): Boolean {
            return oldItem == newItem
        }

    }

}




/*class PackingListRecyclerviewAdapter(
    var context: Context,
    var usedEnumeratorTagList: ArrayList<UsedEnumeratorTag>
): RecyclerView.Adapter<PackingListRecyclerviewAdapter.UsedEnumeratorTagViewHolder>() {

    lateinit var binding: PackingListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsedEnumeratorTagViewHolder {
        binding = PackingListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsedEnumeratorTagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsedEnumeratorTagViewHolder, position: Int) {
        val usedEnumeratorTag = usedEnumeratorTagList[position]
        holder.bind(usedEnumeratorTag)
    }

    override fun getItemCount(): Int = usedEnumeratorTagList.size

    class UsedEnumeratorTagViewHolder(
        private val binding: PackingListItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(usedEnumeratorTag: UsedEnumeratorTag) {
            binding.scanTag.text = usedEnumeratorTag.tag_id
        }

    }

}*/
