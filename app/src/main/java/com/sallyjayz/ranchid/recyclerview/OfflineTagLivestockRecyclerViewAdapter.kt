package com.sallyjayz.ranchid.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import com.sallyjayz.ranchid.R
import com.sallyjayz.ranchid.model.offline.taglivestock.OfflineTagLivestock

class OfflineTagLivestockRecyclerViewAdapter(private val context: Context) : PagingDataAdapter<OfflineTagLivestock,
        OfflineTagLivestockRecyclerViewAdapter.TagLivestockViewHolder>(DiffUtilCallback()) {

    private var onClickListener: OnClickListener? = null

    override fun onBindViewHolder(holder: TagLivestockViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)

            when (it.status) {
                "PENDING" -> {
                    Glide.with(context)
                        .load(R.drawable.ic_pending)
                        .override(50, 60)
                        .into(holder.image)
                }
                "COMPLETED" -> {
                    Glide.with(context)
                        .load(R.drawable.ic_complete)
                        .override(50, 60)
                        .into(holder.image)
                }
                else -> {
                    Glide.with(context)
                        .load(R.drawable.ic_failed)
                        .override(50, 60)
                        .into(holder.image)
                }
            }

            if (it.status == "FAILED") {
                holder.itemView.setOnClickListener {
                    if (onClickListener != null) {
                        getItem(position)?.let { offlineTagLivestock ->
                            onClickListener!!.onClickOfflineTagLivestock(position,
                                offlineTagLivestock
                            )
                        }
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagLivestockViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.offline_list_item, parent, false)
        return TagLivestockViewHolder(inflater)
    }

    class TagLivestockViewHolder (view: View): RecyclerView.ViewHolder(view) {
        private val fullName: MaterialTextView = view.findViewById(R.id.name)
        val image: ImageView = view.findViewById(R.id.status_image)

        fun bind(offlineTagLivestock: OfflineTagLivestock) {
            fullName.text = offlineTagLivestock.tagId
        }

    }

    class DiffUtilCallback : DiffUtil.ItemCallback<OfflineTagLivestock>() {
        override fun areItemsTheSame(
            oldItem: OfflineTagLivestock,
            newItem: OfflineTagLivestock
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: OfflineTagLivestock,
            newItem: OfflineTagLivestock
        ): Boolean {
            return oldItem == newItem
        }

    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClickOfflineTagLivestock(position: Int, offlineTagLivestock: OfflineTagLivestock)
    }
}