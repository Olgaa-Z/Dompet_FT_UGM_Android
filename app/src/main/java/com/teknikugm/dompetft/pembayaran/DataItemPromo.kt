package com.teknikugm.dompetft.pembayaran

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DataItemPromo(


    @SerializedName("id") val id : Int,
    @SerializedName("kode_promo") val kode_promo : String,
    @SerializedName("detail_promo") val detail_promo : String,
    @SerializedName("jumlah_promo") val jumlah_promo : String,
    @SerializedName("persentase_promo") val persentase_promo : Int,
    @SerializedName("min_belanja") val min_belanja : String,
    @SerializedName("jumlah_user") val jumlah_user : String,
    @SerializedName("berlaku_dari") val berlaku_dari : String,
    @SerializedName("berlaku_sampai") val berlaku_sampai : String

//    @field:SerializedName("id_promo")
//    val idPromo: String? = null,
//
//    @field:SerializedName("jumlah_promo")
//    val jumlahPromo: String? = null,
//
//    @field:SerializedName("min_belanja")
//    val minBelanja: String? = null,
//
////    @field:SerializedName("status_promo")
////    val statusPromo: String? = null
//
//    @field:SerializedName("persentase_promo")
//    val persentasePromo: String? = null,
//
//    @field:SerializedName("jumlah_barispromo")
//    val jumlah_barispromo: Int? = null


) : Serializable