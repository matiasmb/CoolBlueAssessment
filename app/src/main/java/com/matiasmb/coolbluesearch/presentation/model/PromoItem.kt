package com.matiasmb.coolbluesearch.presentation.model

sealed class PromoItem {
    class ActionPrice(val promoText: String): PromoItem()
    class CoolBluesChoice(val promoText: String): PromoItem()
}