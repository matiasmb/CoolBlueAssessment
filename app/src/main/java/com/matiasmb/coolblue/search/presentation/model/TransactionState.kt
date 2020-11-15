package com.matiasmb.coolblue.search.presentation.model

sealed class TransactionState {
    object Running : TransactionState()
    object Success : TransactionState()
    object Fail : TransactionState()
}