package com.data.repository.response

typealias SearchKeyResponse = ArrayList<SearchKey>

data class SearchKey(
    val id: Long,
    val title: String,
    val imageType: String
)
