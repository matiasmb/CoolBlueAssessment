package com.matiasmb.coolbluesearch.presentation.model

sealed class ItemsStateScreen {
    class ShowItems(val repos: ArrayList<ItemView>) : ItemsStateScreen()
    object ShowErrorLoading : ItemsStateScreen()
}