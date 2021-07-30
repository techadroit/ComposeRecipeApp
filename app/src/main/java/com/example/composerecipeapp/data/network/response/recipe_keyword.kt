package com.example.composerecipeapp.data.network.response

typealias SearchKeyResponse = ArrayList<SearchKey>

data class SearchKey(
    val id: Long,
    val title: String,
    val imageType: String
)
