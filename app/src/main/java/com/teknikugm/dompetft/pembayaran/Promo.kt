package com.teknikugm.dompetft.pembayaran

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.teknikugm.dompetft.R
import com.teknikugm.dompetft.retrofit.API
import com.teknikugm.dompetft.retrofit.Data
import com.teknikugm.dompetft.retrofit.RetrofitClient
import com.teknikugm.dompetft.retrofit.RetrofitClientDua
import com.teknikugm.dompetft.utama.MainActivity
import kotlinx.android.synthetic.main.activity_promo.*
import kotlinx.android.synthetic.main.activity_promo_adapter.*
import kotlinx.android.synthetic.main.activity_transaksi_pesanan.*
import kotlinx.android.synthetic.main.activity_transfer_saldo.*
import okhttp3.internal.checkOffsetAndCount
import retrofit2.Call
import retrofit2.Response

class Promo : AppCompatActivity() {

    private var key= "hasil"
    private var result : String?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promo)

        var selectedPromo = intent.getStringExtra("kode_promo")

//        val itn = intent.getSerializableExtra("popular") as? DataItem

            ava_promos.setText("Available Promos")
            list_promo(selectedPromo)


        panah_promo.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    fun list_promo(selectedPromo: String?) {

        RetrofitClientDua.instance.create(API::class.java).ambil_promodua()
            .enqueue(object : retrofit2.Callback<List<DataItemPromo>>{

                override fun onResponse(
                    call: Call<List<DataItemPromo>>,
                    response: Response<List<DataItemPromo>>
                ) {
                    if (response.isSuccessful) {
                        val dataItem: List<DataItemPromo?>? = response.body()
                        if (dataItem?.size!! > 0) {
                            //ada isi
                        } else {
                            //nggal ada
                            gambarpromo.setImageResource(R.drawable.icon_pricetag)
                            Toast.makeText(
                                applicationContext,
                                "tidak ada promo",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                            val adapter = PromoAdapter(dataItem, this@Promo) { item ->

                                val x = intent.extras // ini untuk ngambil data yang dikasih sama transaksi pesanan, untuk ambil hasil scannya tu
                                result = x?.getString(key) // nilainya ditarok di sini
                                val totalbelanja = result?.toInt() // nilainya dimasukin ke variabel
                                val y = item?.min_belanja.toString().toInt() // ini untuk ngambil minimal belanja di list promo
                                val row = item?.jumlah_promo.toString()
                                banyakpromo.text = row


//                            val z = y.("Rp","").replace(".","")

                                if (totalbelanja != null) {
                                    if (totalbelanja < y) {
                                        Toast.makeText(applicationContext, "Total belanja Anda masih kurang dari $y", Toast.LENGTH_SHORT).show()
                                    } else if (item?.kode_promo == selectedPromo) {
                                        Toast.makeText(applicationContext, "Promo $selectedPromo sudah Anda pakai", Toast.LENGTH_SHORT).show()
                                    } else {
                                        val ii = Intent()
                                        ii.putExtra("promo", item)
                                        setResult(Activity.RESULT_OK, ii)
                                        finish()
                                    }
                                }
                            }
                            rv.layoutManager = LinearLayoutManager(this@Promo)
                            rv.adapter = adapter
                        }
                    }

                override fun onFailure(call: Call<List<DataItemPromo>>, t: Throwable) {
                    Toast.makeText(applicationContext, "Connection failed", Toast.LENGTH_SHORT).show()
                }
            })
    }

    companion object {
        const val REQUEST_CODE = 2502
    }
}