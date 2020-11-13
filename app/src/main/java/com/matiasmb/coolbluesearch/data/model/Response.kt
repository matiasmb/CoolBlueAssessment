package com.matiasmb.coolbluesearch.data.model

data class Response(
    val products: List<Product>,
    val currentPage: Int,
    val pageSize: Int,
    val pageCount: Int
)