package com.example.test2.data.api

import com.example.test2.data.model.*
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    @Headers("Content-Type: application/json")
    @POST("appAllGoods")
    fun appAllGoods(
        @Body eNo: RequestBody
    ): Call<GoodResponse>

    @Headers("Content-Type: application/json")
    @POST("appAllExhibition")
    fun appAllExhibition(

    ): Call<ExhibitionResponse>

    @Headers("Content-Type: application/json")
    @POST("appAllShoppingcart")
    fun appAllShoppingcart(
        @Body memNo: RequestBody
    ): Call<CartResponse>

    @Headers("Content-Type: application/json")
    @POST("appAddS")
    fun appAddS(
        @Body addSRequest: RequestBody
    ): Call<CartAdd>

    @Headers("Content-Type: application/json")
    @POST("appUpdateS")
    fun appUpdateS(
        @Body updateSRequest: RequestBody // gAmount, scNo
    ): Call<CartAmountUpdate>

    @Headers("Content-Type: application/json")
    @POST("appDeleteS")
    fun appDeleteS(
        @Body deleteSRequest: RequestBody // scNo
    ): Call<CartDelete>

    @Headers("Content-Type: application/json")
    @POST("appMemExhibition")
    fun appMemExhibition(
        @Body memNo: RequestBody // scNo
    ): Call<ExhibitionResponse_memNo>

    @Headers("Content-Type: application/json")
    @POST("appLoginExhibition")
    fun appLoginExhibition(
        @Body pinRequest: RequestBody // memNo, pin
    ): Call<ExhibitionResponse_pin>
}

