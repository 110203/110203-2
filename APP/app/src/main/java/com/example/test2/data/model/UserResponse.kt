package com.example.test2.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class UserResponse(
    @field:Json(name = "status") val status: String,
    @field:Json(name = "data") val data: List<MemDetail>?
)

@JsonClass(generateAdapter = true)
data class MemDetail(
    @field:Json(name = "memName") val memName: String,
    @field:Json(name = "address") val memAddress: String?,
    @field:Json(name = "phone") val memPhone: String?
)

@JsonClass(generateAdapter = true)
data class UserResponseBySignUp(
    @field:Json(name = "status") val status: String
)

@JsonClass(generateAdapter = true)
data class UserUpdate(
    @field:Json(name = "status") val status: String
)
