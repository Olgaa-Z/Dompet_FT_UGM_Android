package com.teknikugm.dompetft.pembayaran

import com.google.gson.annotations.SerializedName

data class ResponsePromo(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("response")
	val response: String? = null,

	@field:SerializedName("status")
	val status: Int? = null

)



