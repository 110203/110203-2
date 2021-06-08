package com.example.test2.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GoodResponse(
    @field:Json(name = "status") val status: String?,
    @field:Json(name = "data") val data: List<GoodDetail>
)

@JsonClass(generateAdapter = true)
data class GoodDetail(
    @field:Json(name = "gNo") val gNo: String,
    @field:Json(name = "gName") val gName: String,
    @field:Json(name = "gImage2D") val gImage: String?,
    @field:Json(name = "introdution") val gIntrodution: String,
    @field:Json(name = "price") val gPrice: Int,
    @field:Json(name = "gAmount") val gAmount: Int
)