package com.teknikugm.dompetft.revisi.transfer

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.teknikugm.dompetft.R
import com.teknikugm.dompetft.pembayaran.Response_Detail
import com.teknikugm.dompetft.retrofit.API
import com.teknikugm.dompetft.retrofit.RetrofitClient
import com.teknikugm.dompetft.retrofit.RetrofitClient.instance
import com.teknikugm.dompetft.revisi.api.ApiClient
import com.teknikugm.dompetft.utama.MainActivity
import kotlinx.android.synthetic.main.activity_transfer_saldo.*
import retrofit2.Call
import retrofit2.Response

class TransferSaldonew : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_saldonew)
    }

//    fun transferSaldo(){
//        val retrofit = ApiClient.instance
//        val api = retrofit.create(API::class.java)
//        val amount_transaksi = editbalance_transfer.text.toString()
//
//        api.transaksi(username, jumlahTransfer.toInt(), username_to).enqueue(
//
//            object : retrofit2.Callback<Response_Detail>{
//                override fun onFailure(call: Call<Response_Detail>, t: Throwable) {
//                    Toast.makeText(this@TransferSaldo, t.message, Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onResponse(
//                    call: Call<Response_Detail>,
//                    response: Response<Response_Detail>
//                ) {
//                    if (response.isSuccessful) {
//                        val message = response.body()?.message
//                        if (response.isSuccessful) {
//                            val message = response.body()?.message
//
//                            AlertDialog.Builder(this@TransferSaldo)
//                                .setMessage("Transaksi sebesar Rp$amount_transaksi berhasil")
//                                .setPositiveButton(android.R.string.ok) { dialog, whichButton ->
//                                    clearData()
//                                    startActivity(Intent(applicationContext, MainActivity::class.java))
//                                }
//                                .show()
//
//                        } else{
//                            Toast.makeText(this@TransferSaldo, message , Toast.LENGTH_SHORT).show()
//                        }
//                    } else {
//                        Toast.makeText(this@TransferSaldo, response.message() , Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//            }
//
//        )
//    }
}