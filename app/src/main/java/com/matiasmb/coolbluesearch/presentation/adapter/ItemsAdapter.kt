package com.matiasmb.coolbluesearch.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.matiasmb.coolbluesearch.R
import com.matiasmb.coolbluesearch.presentation.adapter.holder.ItemViewHolder
import com.matiasmb.coolbluesearch.presentation.model.ItemView

class ItemsAdapter(private val context: Context) :
    PagedListAdapter<ItemView, ItemViewHolder>(PRODUCT_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false),
            context
        )


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBindViewHolder(getItem(position))
        setAnimation(holder.itemView)
    }

    private fun setAnimation(viewToAnimate: View) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.right_in)
        viewToAnimate.startAnimation(animation)
    }

    companion object {
        private val PRODUCT_COMPARATOR = object : DiffUtil.ItemCallback<ItemView>() {
            override fun areItemsTheSame(oldItem: ItemView, newItem: ItemView): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: ItemView, newItem: ItemView): Boolean =
                (oldItem as ItemView.Product).id == (newItem as ItemView.Product).id
        }
    }
}
