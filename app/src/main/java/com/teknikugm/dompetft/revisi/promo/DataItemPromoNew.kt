package com.teknikugm.dompetft.revisi.promo

import com.google.gson.annotations.SerializedName



data class DataItemPromoNew(

	@field:SerializedName("kode_promo")
	val kodePromo: String? = null,

	@field:SerializedName("min_belanja")
	val minBelanja: String? = null,

	@field:SerializedName("jumlah_user")
	val jumlahUser: String? = null,

	@field:SerializedName("persentase_promo")
	val persentasePromo: Int? = null,

	@field:SerializedName("berlaku_sampai")
	val berlakuSampai: String? = null,

	@field:SerializedName("berlaku_dari")
	val berlakuDari: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("detail_promo")
	val detailPromo: String? = null,

	@field:SerializedName("jumlah_promo")
	val jumlahPromo: Any? = null
)
