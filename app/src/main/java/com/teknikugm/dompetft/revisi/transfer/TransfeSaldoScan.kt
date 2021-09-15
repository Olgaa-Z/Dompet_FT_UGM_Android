package com.teknikugm.dompetft.revisi.transfer

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.teknikugm.dompetft.R
import com.teknikugm.dompetft.revisi.api.ApiClient
import com.teknikugm.dompetft.revisi.api.FilterUser
import com.teknikugm.dompetft.utama.MainActivity
import kotlinx.android.synthetic.main.activity_transfe_saldo_scan.*
import kotlinx.android.synthetic.main.activity_transfer_saldo.*
import kotlinx.android.synthetic.main.activity_transfer_saldonew.*
import kotlinx.android.synthetic.main.activity_transfer_saldonew.btn_send_transfer
import retrofit2.Call
import retrofit2.Response

class TransfeSaldoScan : AppCompatActivity() {

    private var listUser = mutableListOf<FilterUser>()
    private var result: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfe_saldo_scan)


        val x = intent.extras
        result = x?.getString("SCANUSER")
        txtuname_transferqr.text= result

        tampiluser()

        btn_transfer_saldo_payqr.setOnClickListener() {
            val username = txtuname_transferqr.text.toString()
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
                        val a = edit_amount_payqr.text.toString().toInt()
                        val c = resp?.id
                        ApiClient().getApiService(this@TransfeSaldoScan).transfer(a, id, c!!)
                            .enqueue(object : retrofit2.Callback<TransferItem> {
                                override fun onResponse(call: Call<TransferItem>, response: Response<TransferItem>) {
                                    AlertDialog.Builder(this@TransfeSaldoScan)
                                            .setMessage("The balance transfer of Rp. $a was successful")
                                            .setPositiveButton(android.R.string.ok) { dialog, whichButton ->
//                                                        clearData()
                                                startActivity(Intent(applicationContext, MainActivity::class.java))
                                            }
                                            .show()
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

}