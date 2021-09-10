package com.teknikugm.dompetft.revisi.bayar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.teknikugm.dompetft.R
import com.teknikugm.dompetft.revisi.api.ApiClient
import com.teknikugm.dompetft.revisi.transfer.BayarItem
import com.teknikugm.dompetft.revisi.transfer.ResponseTransaksiItem
import kotlinx.android.synthetic.main.activity_pay_kantin.*
import retrofit2.Call
import retrofit2.Response
import kotlin.math.log

class PayKantin : AppCompatActivity() {
    private var result : String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_kantin)

        val x = intent.extras
        result = x?.getString("SCAN")
        val isi_pesanan = result

        val gson = Gson()
        val resBayar = gson.fromJson(result, ResponseBayar::class.java)
        isipesanan.text=result
//        isipesanan.text=resBayar.jumlahPesanan.toString()

        totalorder.text= resBayar.jumlahPesanan.toString()
        totalbayar.text= resBayar.jumlahPesanan.toString()

//        UNTUK PRINT DATA KE LOG (genereate model 'response bayar')
        Log.d("TAG", "$isi_pesanan")
        print(result)

        btnpaycanteen.setOnClickListener(){
            val tipetransaksi = 1
            bayarKantin(tipetransaksi)
        }

    }


    private fun bayarKantin(tipetransaksi: Int){
        ApiClient().getApiService(this).transaksinew(tipetransaksi)
            .enqueue(object : retrofit2.Callback<ResponseTransaksiItem> {
                override fun onResponse(call: Call<ResponseTransaksiItem>, response: Response<ResponseTransaksiItem>) {
                    val resp = response.body()
                    if (response.isSuccessful) {
                        val gsondua = Gson()
                        val resBayardua = gsondua.fromJson(result, ResponseBayar::class.java)
                        val totalAsli = resBayardua.jumlahPesanan!!.toInt()
                        val totalBayar = resBayardua.jumlahPesanan
                        val idPenjual = resBayardua.penjual
                        val diskon = 1
                        val idtransaksi = resp!!.id!!.toInt()
                        ApiClient().getApiService(this@PayKantin).bayar(totalAsli,totalBayar,idPenjual,diskon,idtransaksi)
                            .enqueue(object : retrofit2.Callback<BayarItem>{
                                override fun onResponse(
                                    call: Call<BayarItem>,
                                    response: Response<BayarItem>
                                ) {
                                    Toast.makeText(applicationContext, response.message(), Toast.LENGTH_SHORT).show()
                                }

                                override fun onFailure(call: Call<BayarItem>, t: Throwable) {
                                    Toast.makeText(applicationContext, response.message(), Toast.LENGTH_SHORT).show()
                                }

                            })
                        Toast.makeText(applicationContext, response.message(), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, response.message(), Toast.LENGTH_SHORT).show()
                    }
                    print(response.body())
                }

                override fun onFailure(call: Call<ResponseTransaksiItem>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                    t.printStackTrace()
                }

            })
    }
}