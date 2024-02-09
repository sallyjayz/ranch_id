package com.sallyjayz.ranchid.recyclerview.offline

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
import com.sallyjayz.ranchid.model.offline.keeper.OfflineKeeper

class OfflineKeeperRecyclerViewAdapter(private val context: Context): PagingDataAdapter<OfflineKeeper,
        OfflineKeeperRecyclerViewAdapter.KeeperViewHolder>(DiffUtilCallback()) {

    private var onClickListener: OnClickListener? = null

    override fun onBindViewHolder(holder: KeeperViewHolder, position: Int) {
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
                        getItem(position)?.let { offlineKeeper ->
                            onClickListener!!.onClickOfflineKeeper(position,
                                offlineKeeper
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeeperViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.offline_list_item, parent, false)
        return KeeperViewHolder(inflater)
    }


    class KeeperViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val fullName: MaterialTextView = view.findViewById(R.id.name)
        val image: ImageView = view.findViewById(R.id.status_image)

        fun bind(offlineKeeper: OfflineKeeper) {
            fullName.text = "${offlineKeeper.surname} ${offlineKeeper.otherNames}"
        }
    }


    class DiffUtilCallback : DiffUtil.ItemCallback<OfflineKeeper>() {
        override fun areItemsTheSame(oldItem: OfflineKeeper, newItem: OfflineKeeper): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: OfflineKeeper, newItem: OfflineKeeper): Boolean {
            return oldItem == newItem
        }

    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClickOfflineKeeper(position: Int, offlineKeeper: OfflineKeeper)
    }
}