package com.example.test2.data.model

import android.graphics.Bitmap
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.sql.Timestamp
import java.time.DateTimeException
import java.util.*

@JsonClass(generateAdapter = true)
data class ExhibitionResponse(
    @field:Json(name = "status") val status: String?,
    @field:Json(name = "data") val data: List<ExhibitionDetail>?
)

@JsonClass(generateAdapter = true)
data class ExhibitionDetail(
    @field:Json(name = "eNo") val eNo: String,
    @field:Json(name = "eName") val eName: String,
    @field:Json(name = "eType") val eType: String,
    @field:Json(name = "eImage") var eImage: String?,
    @field:Json(name = "introdution") val eIntrodution: String,
    @field:Json(name = "startTime") val startTime: String,
    @field:Json(name = "endTime") val endTime: String,
    @field:Json(name = "eFile2D") val eFile2D: String?
)

// 會員 > 申請建展
@JsonClass(generateAdapter = true)
data class ExhibitionAdd(
    @field:Json(name = "status") val status: String?
)

// 會員 > 管理展覽
@JsonClass(generateAdapter = true)
data class ExhibitionResponseByMemNo(
    @field:Json(name = "status") val status: String?,
    @field:Json(name = "data") val data: List<ExhibitionDetailByMemNo>?
)

@JsonClass(generateAdapter = true)
data class ExhibitionDetailByMemNo(
    @field:Json(name = "eNo") val eNo: String?,
    @field:Json(name = "eName") val eName: String?,
    @field:Json(name = "eType") val eType: String?,
    @field:Json(name = "eImage") var eImage: String?,
    @field:Json(name = "introdution") val eIntrodution: String?,
    @field:Json(name = "startTime") val startTime: String?, // 可看不可修
    @field:Json(name = "endTime") val endTime: String?, // 可看不可修
    @field:Json(name = "eCheck") val eCheck: Int?,
    @field:Json(name = "login") val eLogin: Int?,
    @field:Json(name = "style") val eStyle: String?
)

// 會員 > 管理展覽 > 輸入PIN碼
@JsonClass(generateAdapter = true)
data class ExhibitionResponseByPin(
    @field:Json(name = "status") val status: String,
    @field:Json(name = "data") val data: Int
)