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
import com.sallyjayz.ranchid.model.offline.owner.OfflineOwner

class OfflineOwnerRecyclerViewAdapter(private val context: Context) : PagingDataAdapter<OfflineOwner,
        OfflineOwnerRecyclerViewAdapter.OwnerViewHolder>(DiffUtilCallback()) {

    private var onClickListener: OnClickListener? = null

    override fun onBindViewHolder(holder: OwnerViewHolder, position: Int) {
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
                        getItem(position)?.let { offlineOwner ->
                            onClickListener!!.onClickOfflineOwner(position,
                                offlineOwner
                            )
                        }
                    }
                }
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnerViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.offline_list_item, parent, false)
        return OwnerViewHolder(inflater)
    }


    class OwnerViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val fullName: MaterialTextView = view.findViewById(R.id.name)
        val image: ImageView = view.findViewById(R.id.status_image)

        fun bind(offlineOwner: OfflineOwner) {
            fullName.text = "${offlineOwner.surname} ${offlineOwner.otherNames}"
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<OfflineOwner>() {
        override fun areItemsTheSame(oldItem: OfflineOwner, newItem: OfflineOwner): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: OfflineOwner, newItem: OfflineOwner): Boolean {
            return oldItem == newItem
        }

    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClickOfflineOwner(position: Int, offlineOwner: OfflineOwner)
    }
}

