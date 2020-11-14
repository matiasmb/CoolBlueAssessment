package com.matiasmb.coolbluesearch.presentation.model

sealed class TransactionState {
    object Running: TransactionState()
    object Success: TransactionState()
    object Fail: TransactionState()
}