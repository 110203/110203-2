package com.example.test2.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class OrderResponse(
    @field:Json(name = "status") val status: String?,
    @field:Json(name = "data") val data: List<OrderDetail>
)

@JsonClass(generateAdapter = true)
data class OrderDetail(
    @field:Json(name = "orNo") val orNo: String,
    @field:Json(name = "orTime") val orTime: String,
    @field:Json(name = "state") val orState: String
)
