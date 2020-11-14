package com.matiasmb.coolbluesearch.presentation.adapter.holder

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.RecyclerView
import com.matiasmb.coolbluesearch.presentation.formatAndSetText
import com.matiasmb.coolbluesearch.presentation.loadImage
import com.matiasmb.coolbluesearch.presentation.model.ItemView
import com.matiasmb.coolbluesearch.presentation.parseCurrency
import kotlinx.android.synthetic.main.item_product.view.*

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun onBindViewHolder(data: ItemView?) {
        val holderData = data as? ItemView.Product
        holderData?.let { productItemViewData ->
            itemView.apply {
                product_name.text = productItemViewData.name
                product_image.loadImage(productItemViewData.imageUrl, itemView.context)
                product_price.parseCurrency(productItemViewData.price)
                product_stock.formatAndSetText(productItemViewData.stock.toString())
                product_extra_info.text = productItemViewData.extraInfo.toString()
                product_delivery_express.visibility =
                    if (productItemViewData.nextDayDeliveryFlag) VISIBLE else GONE
                product_rating_bar.rating = productItemViewData.reviewAverage.toFloat()
                product_rating_info.text =
                    "${productItemViewData.reviewAverage} over ${productItemViewData.reviewCount} votes"
            }
        }
    }
}