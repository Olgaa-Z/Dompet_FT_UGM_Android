package com.teknikugm.dompetft.revisi.promo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.teknikugm.dompetft.R
import com.teknikugm.dompetft.pembayaran.DataItemPromo
import com.teknikugm.dompetft.pembayaran.PromoAdapter
import com.teknikugm.dompetft.retrofit.API
import com.teknikugm.dompetft.retrofit.RetrofitClientDua
import com.teknikugm.dompetft.revisi.api.ApiClient
import com.teknikugm.dompetft.revisi.bayar.PayKantin
import com.teknikugm.dompetft.utama.MainActivity
import kotlinx.android.synthetic.main.activity_promo.*
import retrofit2.Call
import retrofit2.Response

class PromoNew : AppCompatActivity() {

    private var key= "hasil"
    private var result : String?= null
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promo_new)

        progressBar = findViewById(R.id.pbpromo)

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
                    showLoading()
                    if (response.isSuccessful) {
                        val dataItem: List<DataItemPromoNew?>? = response.body()
                        if (dataItem?.size!! > 0) {
                            //ada isi
                        } else {
                            //nggak ada
                            gambarpromo.setImageResource(R.drawable.icon_pricetag)
                            Toast.makeText(
                                applicationContext,
                                "there is no promo",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        val adapter = PromoNewAdapter(dataItem, this@PromoNew) { item ->
                            val backIntent = Intent()
                            backIntent.putExtra(DATA_PROMO, item)
                            setResult(Activity.RESULT_OK, backIntent)
                            finish()

                        }
                        rv.layoutManager = LinearLayoutManager(this@PromoNew)
                        rv.adapter = adapter
                        hideLoading()
                    }
                }

                override fun onFailure(call: Call<List<DataItemPromoNew>>, t: Throwable) {
                    Toast.makeText(applicationContext, "Connection failed", Toast.LENGTH_SHORT).show()
                }


            })

    }

    fun showLoading() {
        progressBar.visibility
    }

     fun hideLoading() {
         progressBar.setVisibility(View.GONE)
    }

    companion object {
        const val REQUEST_CODE = 2502
        const val DATA_PROMO = "data promo"
    }

}