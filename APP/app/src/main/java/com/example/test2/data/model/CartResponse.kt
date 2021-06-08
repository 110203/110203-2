package com.example.test2.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class CartResponse(
    @field:Json(name = "status") val status: String?,
    @field:Json(name = "data") val data: List<CartDetail>
)

@JsonClass(generateAdapter = true)
data class CartDetail(
    @field:Json(name = "gNo") val gNo: String,
    @field:Json(name = "gName") val gName: String,
    @field:Json(name = "gImage2D") val gImage: String?,
    @field:Json(name = "price") val gPrice: Int,
    @field:Json(name = "gAmount") val gAmount: Int
)

@JsonClass(generateAdapter = true)
data class CartAdd(
    @field:Json(name = "status") val status: String?
)

@JsonClass(generateAdapter = true)
data class CartAmountUpdate(
    @field:Json(name = "status") val status: String
)

@JsonClass(generateAdapter = true)
data class CartDelete(
    @field:Json(name = "status") val status: String
)