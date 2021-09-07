package com.teknikugm.dompetft.revisi.api

import com.teknikugm.dompetft.revisi.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    //@Headers("Content-Type: application/json")
    @POST(Constants.REGISTER_URL)
    //@FormUrlEncoded
    fun addUser(@Body userInfo: RegisterRequest): Call<RegisterResponse>

    //@Headers("Content-Type: application/json")
    @POST(Constants.LOGIN_URL)
    //@FormUrlEncoded
    fun login(@Body request: LoginRequest): Call<LoginResponse>

//    @GET(Constants.DEVICES_DETAIL_URL+"{id}")
//    fun getDeviceDetails(@Path("id") id: Int): Call<Device>
//
//    @GET(Constants.DEVICES_LIST_URL)
//    fun getDevices(): Call<ArrayList<DevicesResponse>>
//
//    @FormUrlEncoded
//    @PUT(Constants.DEVICES_DETAIL_URL+"{id}")
//    fun updateDevice(@Path("id") id: Int,
//                     @Field("name") namaalat: String,
//                     @Field("loc_lat") loc_lat: Float,
//                     @Field("loc_long") loc_long: Float,
//                     @Field("is_public") is_public: Boolean
//    ): Call<Device>
//
    @GET(Constants.USER_URL)
    fun getProfile(): Call<Profile_m>
//
//    @FormUrlEncoded
//    @PUT(Constants.USER_URL)
//    fun changeProfile(@Field("username") username: String,
//                      @Field("first_name") namadepan: String,
//                      @Field("last_name") namabelakang: String
//    ): Call<Profile>
//
//    @POST(Constants.CHANGEPASS_URL)
//    fun changePass(@Body changepass: Changepass): Call<ChangepassResponse>
//
//    @DELETE(Constants.DEVICES_DETAIL_URL+"{id}")
//    fun deleteDevice(@Path("id") id: Int): Call<Device>
//
//    @POST(Constants.DEVICES_LIST_URL)
//    fun addDevice(@Body addDeviceRequest: AddDeviceRequest): Call<AddDeviceResponse>
}