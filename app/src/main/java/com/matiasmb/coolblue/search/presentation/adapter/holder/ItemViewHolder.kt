package com.matiasmb.coolblue.search.presentation.adapter.holder

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.matiasmb.coolblue.R
import com.matiasmb.coolblue.search.presentation.formatAndSetText
import com.matiasmb.coolblue.search.presentation.loadImage
import com.matiasmb.coolblue.search.presentation.model.ItemView
import com.matiasmb.coolblue.search.presentation.model.PromoItem
import com.matiasmb.coolblue.search.presentation.parseCurrency
import kotlinx.android.synthetic.main.item_product.view.*

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun onBindViewHolder(data: ItemView?) {
        data?.let { productItemViewData ->
            itemView.apply {
                product_name.text = productItemViewData.name
                product_image.loadImage(productItemViewData.imageUrl, itemView.context)
                product_price.parseCurrency(productItemViewData.price)
                product_stock.formatAndSetText(productItemViewData.stock.toString())
                product_extra_info.text = productItemViewData.extraInfo.toString()
                product_delivery_express.visibility =
                    if (productItemViewData.nextDayDeliveryFlag) VISIBLE else GONE
                setRatingComponent(productItemViewData)
                setPromoItemComponent(productItemViewData)
                setChoiceTitleComponent(productItemViewData)
            }
        }
    }

    private fun setRatingComponent(productItemViewData: ItemView) {
        itemView.apply {
            product_rating_bar.rating = productItemViewData.reviewAverage.toFloat()
            product_rating_info.text =
                "${productItemViewData.reviewAverage} over ${productItemViewData.reviewCount} votes"
        }
    }

    private fun setPromoItemComponent(productItemViewData: ItemView) {
        itemView.apply {
            when (productItemViewData.promoItem) {
                is PromoItem.ActionPrice -> {
                    promo_text.visibility = VISIBLE
                    promo_text.text = productItemViewData.promoItem.promoText
                    promo_text.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.promo_action_price
                        )
                    )
                }
                is PromoItem.CoolBluesChoice -> {
                    promo_text.visibility = VISIBLE
                    promo_text.text = productItemViewData.promoItem.promoText
                    promo_text.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.coolblues_choice
                        )
                    )
                }
                else -> promo_text.visibility = GONE
            }
        }
    }

    private fun setChoiceTitleComponent(productItemViewData: ItemView) {
        itemView.apply {
            productItemViewData.choiceTitle?.let {
                product_choice_title.text = it
                product_choice_title.visibility = VISIBLE
            }
        }
    }
}