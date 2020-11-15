package com.matiasmb.coolblue.search.presentation.model

sealed class PromoItem {
    class ActionPrice(val promoText: String): PromoItem()
    class CoolBluesChoice(val promoText: String): PromoItem()
}