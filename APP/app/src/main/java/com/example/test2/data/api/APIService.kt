package com.example.test2.data.api

import com.example.test2.data.model.*
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface APIService {

    //////////////////////////
    ////////// USER //////////
    //////////////////////////
    // 使用者註冊
    @Headers("Content-Type: application/json")
    @POST("appAddMember")
    fun appAddMember(
        @Body signupRequest: RequestBody
    ): Call<UserResponseBySignUp>

    // 使用者登入
    @Headers("Content-Type: application/json")
    @POST("appLogin")
    fun appLogin(
        @Body loginRequest: RequestBody
    ): Call<UserResponse>

    // 個人資料查詢
    @Headers("Content-Type: application/json")
    @POST("appMemberProfile")
    fun appMemberProfile(
        @Body queryRequest: RequestBody
    ): Call<UserResponse>

    // 個人資料更改
    @Headers("Content-Type: application/json")
    @POST("appUpdateMember")
    fun appUpdateMember(
        @Body updateRequest: RequestBody
    ): Call<UserUpdate>

    // 密碼更改
    @Headers("Content-Type: application/json")
    @POST("appUpdateMemberPwd")
    fun appUpdateMemberPwd(
        @Body updateRequest: RequestBody
    ): Call<UserUpdate>

    //////////////////////////
    ////////// GOOD //////////
    //////////////////////////
    // {eNo}展覽的所有商品
    @Headers("Content-Type: application/json")
    @POST("appAllGoods")
    fun appAllGoods(
        @Body eNo: RequestBody
    ): Call<GoodResponse>

    ////////////////////////////////
    ////////// EXHIBITION //////////
    ////////////////////////////////
    // 顯示所有展覽
    @Headers("Content-Type: application/json")
    @POST("appAllExhibition")
    fun appAllExhibition(

    ): Call<ExhibitionResponse>

    // 申請建展
    @Headers("Content-Type: application/json")
    @POST("appAddExhibition")
    fun appAddExhibition(
        @Body addRequest: RequestBody
    ): Call<ExhibitionAdd>

    // eCheck=1 && eLogin=1 已審核通過且已輸入pin碼
    @Headers("Content-Type: application/json")
    @POST("appMemExhibition")
    fun appMemExhibitionChecked(
        @Body memNo: RequestBody // memNo
    ): Call<ExhibitionResponseByMemNo>

    // eCheck=0 全部的申請建展
    @Headers("Content-Type: application/json")
    @POST("appAllMemExhibition")
    fun appMemExhibition(
        @Body memNo: RequestBody // memNo
    ): Call<ExhibitionResponseByMemNo>

    @Headers("Content-Type: application/json")
    @POST("appLoginExhibition")
    fun appLoginExhibition(
        @Body pinRequest: RequestBody // memNo, pin
    ): Call<ExhibitionResponseByPin>

    ///////////////////////////////////
    ////////// SHOPPING CART //////////
    ///////////////////////////////////
    // 查看個人購物車
    @Headers("Content-Type: application/json")
    @POST("appAllShoppingcart")
    fun appAllShoppingcart(
        @Body memNo: RequestBody
    ): Call<CartResponse>

    // 新增{gNo}商品至購物車
    @Headers("Content-Type: application/json")
    @POST("appAddS")
    fun appAddS(
        @Body addSRequest: RequestBody
    ): Call<CartAdd>

    // 更改購物車內容
    @Headers("Content-Type: application/json")
    @POST("appUpdateS")
    fun appUpdateS(
        @Body updateSRequest: RequestBody // gAmount, scNo
    ): Call<CartAmountUpdate>

    // 刪除購物車內容
    @Headers("Content-Type: application/json")
    @POST("appDeleteS")
    fun appDeleteS(
        @Body deleteSRequest: RequestBody // scNo
    ): Call<CartDelete>

    ///////////////////////////
    ////////// ORDER //////////
    ///////////////////////////
    // 查看訂單
    @Headers("Content-Type: application/json")
    @POST("appOrderRecord")
    fun appOrderRecord(
        @Body memNo: RequestBody
    ): Call<OrderResponse>
}

