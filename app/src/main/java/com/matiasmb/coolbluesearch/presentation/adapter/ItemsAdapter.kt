package com.matiasmb.coolbluesearch.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.brandongogetap.stickyheaders.exposed.StickyHeaderHandler
import com.matiasmb.coolbluesearch.R
import com.matiasmb.coolbluesearch.presentation.adapter.holder.ItemViewHolder
import com.matiasmb.coolbluesearch.presentation.model.ItemView

class ItemsAdapter(
    private val items: ArrayList<ItemView>,
    private val context: Context
) :
    RecyclerView.Adapter<ItemViewHolder>(), StickyHeaderHandler {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.onBindViewHolder(items[position])
        setAnimation(holder.itemView)
    }

    override fun getAdapterData(): ArrayList<ItemView> = items

    override fun getItemCount(): Int = items.size

    private fun setAnimation(viewToAnimate: View) {
        val animation = AnimationUtils.loadAnimation(context, R.anim.right_in)
        viewToAnimate.startAnimation(animation)
    }
}
