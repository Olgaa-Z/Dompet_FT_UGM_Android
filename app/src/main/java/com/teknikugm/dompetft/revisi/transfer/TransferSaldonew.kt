package com.teknikugm.dompetft.revisi.transfer

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.teknikugm.dompetft.R
import com.teknikugm.dompetft.revisi.api.ApiClient
import com.teknikugm.dompetft.revisi.api.FilterUser
import kotlinx.android.synthetic.main.activity_transfer_saldo.*
import kotlinx.android.synthetic.main.activity_transfer_saldo.btn_send_transfer
import kotlinx.android.synthetic.main.activity_transfer_saldonew.*
import retrofit2.Call
import retrofit2.Response

class TransferSaldonew : AppCompatActivity() {
    private var listUser = mutableListOf<FilterUser>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_saldonew)

        tampiluser()

        btn_send_transfer.setOnClickListener() {
            val username = usernametransfer.text.toString()
            val filter = listUser.filter {
                it.username == username.toString()
            }
            val id = filter[0].id
            val tipetrans = 2
            transaksiNew(tipetrans, id)
        }
    }

    private fun transaksiNew(tipetransaksi: Int, id: Int?) {
        ApiClient().getApiService(this).transaksinew(tipetransaksi)
                .enqueue(object : retrofit2.Callback<ResponseTransaksiItem> {
                    override fun onResponse(call: Call<ResponseTransaksiItem>, response: Response<ResponseTransaksiItem>) {
                        val resp = response.body()
                        if (response.isSuccessful) {
                            val a = jumlahtransfernew.text.toString().toInt()
                            val c = resp?.id
                            ApiClient().getApiService(this@TransferSaldonew).transfer(a, id, c!!)
                                    .enqueue(object : retrofit2.Callback<TransferItem> {
                                        override fun onResponse(call: Call<TransferItem>, response: Response<TransferItem>) {
                                            Toast.makeText(applicationContext, "Transaction oke", Toast.LENGTH_SHORT).show()
                                        }

                                        override fun onFailure(call: Call<TransferItem>, t: Throwable) {
                                            Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                                        }
                                    })
                        } else {
                            Toast.makeText(applicationContext, "Transaction failed", Toast.LENGTH_SHORT).show()
                        }
                        print(response.body())
                    }

                    override fun onFailure(call: Call<ResponseTransaksiItem>, t: Throwable) {
//                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                        t.printStackTrace()
                    }

                })
    }

    private fun tampiluser() {
        ApiClient().getApiService(this).filteruser()
                .enqueue(object : retrofit2.Callback<List<FilterUser>> {
                    override fun onResponse(call: Call<List<FilterUser>>, response: Response<List<FilterUser>>) {
                        if (response.isSuccessful) {
                            listUser.addAll(response.body()!!)
                        }
//                        if (listUser.isNotEmpty()) {
//                            Toast.makeText(this@TransferSaldonew, listUser[0].username, Toast.LENGTH_SHORT).show()
//                        }
                    }

                    override fun onFailure(call: Call<List<FilterUser>>, t: Throwable) {

                    }

                })
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