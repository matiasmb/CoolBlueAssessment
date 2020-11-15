package com.matiasmb.coolblue.search.presentation.model

sealed class TransactionState<out T> {
    object Running : TransactionState<Nothing>()
    class LoadData<T>(val data: T) : TransactionState<T>()
    object EndLoadData: TransactionState<Nothing>()
    object Fail : TransactionState<Nothing>()
}