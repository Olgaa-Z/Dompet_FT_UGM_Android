package com.teknikugm.dompetft.pembayaran

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.teknikugm.dompetft.R
import com.teknikugm.dompetft.retrofit.API
import com.teknikugm.dompetft.retrofit.RetrofitClient
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
        var count = 2

        if(count<1){
            setOf(R.drawable.icon_pricetag)
            gambarpromo.setImageResource(R.drawable.icon_pricetag)
            Toast.makeText(this, "tidak ada promo", Toast.LENGTH_SHORT).show()

        }else{
            ava_promos.setText("Available Promos")
            list_promo(selectedPromo)
        }

        panah_promo.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    fun list_promo(selectedPromo: String?) {

        RetrofitClient.instance.create(API::class.java).ambil_promo()
            .enqueue(object : retrofit2.Callback<ResponsePromo>{

                override fun onResponse(
                    call: Call<ResponsePromo>,
                    response: Response<ResponsePromo>
                ) {
                    if (response.isSuccessful){
                        val dataItem = response.body()?.data
                        val adapter = PromoAdapter(dataItem, this@Promo){ item->

                            val x = intent.extras // ini untuk ngambil data yang dikasih sama transaksi pesanan, untuk ambil hasil scannya tu
                            result = x?.getString(key) // nilainya ditarok di sini
                            val totalbelanja = result?.toInt() // nilainya dimasukin ke variabel
                            val y = item?.minBelanja.toString().toInt() // ini untuk ngambil minimal belanja di list promo



//                            val z = y.("Rp","").replace(".","")

                                if(totalbelanja != null){
                                    if(totalbelanja < y){
                                        Toast.makeText(applicationContext, "Total belanja Anda masih kurang dari $y", Toast.LENGTH_SHORT).show()
                                    }else if(item?.kodePromo == selectedPromo){
                                        Toast.makeText(applicationContext, "Promo $selectedPromo sudah Anda pakai", Toast.LENGTH_SHORT).show()
                                    }else {
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

                override fun onFailure(call: Call<ResponsePromo>, t: Throwable) {
                    Toast.makeText(applicationContext, "Connection failed", Toast.LENGTH_SHORT).show()
                }
            })
    }

    companion object {
        const val REQUEST_CODE = 2502
    }
}