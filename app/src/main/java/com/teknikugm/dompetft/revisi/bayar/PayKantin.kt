package com.teknikugm.dompetft.revisi.bayar

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.teknikugm.dompetft.R
import com.teknikugm.dompetft.revisi.api.ApiClient
import com.teknikugm.dompetft.revisi.api.SessionManager
import com.teknikugm.dompetft.revisi.promo.DataItemPromoNew
import com.teknikugm.dompetft.revisi.promo.PromoNew
import com.teknikugm.dompetft.revisi.topup.TopupSaldo
import com.teknikugm.dompetft.revisi.transfer.BayarItem
import com.teknikugm.dompetft.revisi.transfer.ResponseTransaksiItem
import com.teknikugm.dompetft.utama.MainActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_pay_kantin.*
import kotlinx.android.synthetic.main.activity_topup_saldo.*
import kotlinx.android.synthetic.main.activity_transaksi_pesanan.*
import kotlinx.android.synthetic.main.fragment_profile_fragment.*
import kotlinx.android.synthetic.main.listmenu.*
import retrofit2.Call
import retrofit2.Response

class PayKantin : AppCompatActivity() {

    private var result: String? = null
    private var idPromo: Int? = null
    private var persendiskon: Int? = null
    private var potongandiskon : Int?= null
    private var totalBayar : Int?= null
    private var saldo : String?= null
    private var totaltext : String?= null
    private val key = "hasil"

    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_kantin)

        sessionManager = SessionManager(this)
        if (sessionManager.fetchAuthToken() == null) {
            txtid.text = "Guest"
        }
        else {
            val detailProfile = sessionManager.getProfile()
            saldo = detailProfile.saldo
        }

        val x = intent.extras
        result = x?.getString("SCAN")
        val isi_pesanan = result

        val gson = Gson()
        val resBayar = gson.fromJson(result, ResponseBayar::class.java)
        isipesanan.text=result

//        LIST DAFTAR MENU
        var resMenu = resBayar.daftarMenu
        var array = mutableListOf<String?>()
        resMenu?.forEach {
            array.add(it?.daftarMenu)
        }
        val adapter = ArrayAdapter(this,
            R.layout.listmenu, array)
        val listView:ListView = findViewById(R.id.listmenu)
        listView.setAdapter(adapter)

        totalorder.text= resBayar.jumlahPesanan.toString()
        totaltext = resBayar.jumlahPesanan.toString()
        totalbayar.text=  totaltext

//        UNTUK PRINT DATA KE LOG (genereate model 'response bayar')
        Log.d("TAG", "$isi_pesanan")
        print(result)

        btnpaycanteen.setOnClickListener() {

            if (saldo!!.toInt() < resBayar.jumlahPesanan!!.toInt() ){
                Toast.makeText(this, "Your balance is not enough !", Toast.LENGTH_SHORT).show()
                AlertDialog.Builder(this@PayKantin)

                        .setTitle("Your balance is not enough... ")
                        .setMessage("Fill your balance now")
                        .setPositiveButton("TOP UP") { dialog, whichButton ->
                            startActivity(Intent(this@PayKantin, TopupSaldo::class.java))
                        }
                        .setNegativeButton("CLOSE") { dialog, whichButton ->
                            startActivity(Intent(this@PayKantin, MainActivity::class.java))
                        }
                        .show()
            }
            else{
                val tipetransaksi = 1
                bayarKantin(tipetransaksi)
            }
        }

        btnpromo.setOnClickListener() {
            val i = Intent(this, PromoNew::class.java) // ini dia nge intent ke kelas promo
            startActivityForResult((i), REQUEST_CODE) // tu ini startactivity kalo bawa nilai, jdi startactivityforresult
        }


    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            val dataPromo = data?.getSerializableExtra(PromoNew.DATA_PROMO) as DataItemPromoNew
            Toast.makeText(this, dataPromo.id.toString()+ " persen :" + dataPromo.persentasePromo.toString(), Toast.LENGTH_SHORT).show()
            idPromo = dataPromo.id
            persendiskon = dataPromo.persentasePromo

        }
    }


    private fun bayarKantin(tipetransaksi: Int) {
        ApiClient().getApiService(this).transaksinew(tipetransaksi)
                .enqueue(object : retrofit2.Callback<ResponseTransaksiItem> {
                    override fun onResponse(call: Call<ResponseTransaksiItem>, response: Response<ResponseTransaksiItem>) {
                        val resp = response.body()
                        if (response.isSuccessful) {
                            val gsondua = Gson()
                            val resBayardua = gsondua.fromJson(result, ResponseBayar::class.java)
                            val totalAsli = resBayardua.jumlahPesanan!!.toInt()
                            val idPenjual = resBayardua.penjual
                            val diskon = idPromo
                            val idtransaksi = resp!!.id!!.toInt()

                            if (idPromo != null){
                                potongandiskon= persendiskon
                                val discc =  0.01 * potongandiskon!!
                                val disc = discc * totalAsli
                                totalBayar = totalAsli - disc.toInt()

                            }else{
                                potongandiskon = 0
                                totalBayar= totalAsli - potongandiskon!!
                            }

                            val saldoterkini = saldo?.toInt()!! - totalBayar!!
                            AlertDialog.Builder(this@PayKantin)

                                    .setTitle("Payment transaction worth $totalBayar successful")
                                    .setMessage("Your balance now : $saldoterkini")
                                    .setPositiveButton("OK") { dialog, whichButton ->
                                        startActivity(Intent(this@PayKantin, MainActivity::class.java))
                                    }
                                    .setNegativeButton("CLOSE") { dialog, whichButton ->
                                        startActivity(Intent(this@PayKantin, MainActivity::class.java))
                                    }
                                    .show()
//
                            ApiClient().getApiService(this@PayKantin).bayar(totalAsli, totalBayar, idPenjual, diskon, idtransaksi)
                                    .enqueue(object : retrofit2.Callback<BayarItem> {
                                        override fun onResponse(
                                                call: Call<BayarItem>,
                                                response: Response<BayarItem>
                                        ) {
                                            ApiClient().getApiService(this@PayKantin)
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

    companion object {
        const val REQUEST_CODE = 2502
    }
}