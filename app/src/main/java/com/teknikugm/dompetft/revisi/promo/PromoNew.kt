package com.teknikugm.dompetft.revisi.promo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.teknikugm.dompetft.R
import com.teknikugm.dompetft.pembayaran.DataItemPromo
import com.teknikugm.dompetft.pembayaran.PromoAdapter
import com.teknikugm.dompetft.retrofit.API
import com.teknikugm.dompetft.retrofit.RetrofitClientDua
import com.teknikugm.dompetft.revisi.api.ApiClient
import com.teknikugm.dompetft.utama.MainActivity
import kotlinx.android.synthetic.main.activity_promo.*
import retrofit2.Call
import retrofit2.Response

class PromoNew : AppCompatActivity() {

    private var key= "hasil"
    private var result : String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promo_new)
        var selectedPromo = intent.getStringExtra("kode_promo")

//        val itn = intent.getSerializableExtra("popular") as? DataItem

        ava_promos.setText("Available Promos")
        list_promo(selectedPromo)


        panah_promo.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
        }

    }


    fun list_promo(selectedPromo: String?) {

        ApiClient().getApiService(this).promonew()
            .enqueue(object : retrofit2.Callback<List<DataItemPromoNew>>{

                override fun onResponse(
                    call: Call<List<DataItemPromoNew>>,
                    response: Response<List<DataItemPromoNew>>
                ) {
                    if (response.isSuccessful) {
                        val dataItem: List<DataItemPromoNew?>? = response.body()
                        if (dataItem?.size!! > 0) {
                            //ada isi
                        } else {
                            //nggak ada
                            gambarpromo.setImageResource(R.drawable.icon_pricetag)
                            Toast.makeText(
                                applicationContext,
                                "tidak ada promo",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        val adapter = PromoNewAdapter(dataItem, this@PromoNew) { item ->

                            val x = intent.extras // ini untuk ngambil data yang dikasih sama transaksi pesanan, untuk ambil hasil scannya tu
                            result = x?.getString(key) // nilainya ditarok di sini
                            val totalbelanja = result?.toInt() // nilainya dimasukin ke variabel
                            val y = item?.minBelanja.toString().toInt() // ini untuk ngambil minimal belanja di list promo
                            val row = item?.jumlahPromo.toString()
                            banyakpromo.text = row


//                            val z = y.("Rp","").replace(".","")

                            if (totalbelanja != null) {
                                if (totalbelanja < y) {
                                    Toast.makeText(applicationContext, "Total belanja Anda masih kurang dari $y", Toast.LENGTH_SHORT).show()
                                } else if (item?.kodePromo == selectedPromo) {
                                    Toast.makeText(applicationContext, "Promo $selectedPromo sudah Anda pakai", Toast.LENGTH_SHORT).show()
                                } else {
//                                    val ii = Intent()
//                                    ii.putExtra("promo", item)
//                                    setResult(RESULT_OK, ii)
                                    finish()
                                }
                            }
                        }
                        rv.layoutManager = LinearLayoutManager(this@PromoNew)
                        rv.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<List<DataItemPromoNew>>, t: Throwable) {
                    Toast.makeText(applicationContext, "Connection failed", Toast.LENGTH_SHORT).show()
                }


            })
    }

    companion object {
        const val REQUEST_CODE = 2502
    }

}