package com.matiasmb.coolbluesearch.data.model

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    object Failure: Resource<Nothing>()
}