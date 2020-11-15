package com.matiasmb.coolblue.search.presentation

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.matiasmb.coolblue.R
import java.text.NumberFormat
import java.util.*

fun ImageView.loadImage(imageUrl: String, context: Context) {
    Glide.with(context)
        .load(imageUrl)
        .fitCenter()
        .error(android.R.drawable.ic_dialog_alert)
        .into(this)
}

fun TextView.parseCurrency(amount: Double) {
    NumberFormat.getCurrencyInstance().run {
        maximumFractionDigits = 2
        currency = Currency.getInstance("EUR")

        text = format(amount)
    }

}

fun TextView.formatAndSetText(parameterText: String) {
    text = this.resources.getString(R.string.product_available, parameterText)
}
