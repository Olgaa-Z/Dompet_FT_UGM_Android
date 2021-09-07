package com.teknikugm.dompetft.revisi.topup

import android.app.AlertDialog
import android.content.ContextWrapper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.teknikugm.dompetft.R
import com.teknikugm.dompetft.pembayaran.DataItemPromo
import com.teknikugm.dompetft.retrofit.API
import com.teknikugm.dompetft.retrofit.Constant
import com.teknikugm.dompetft.retrofit.RetrofitClientDua
import com.teknikugm.dompetft.revisi.api.ApiClient
import com.teknikugm.dompetft.revisi.api.SessionManager
import com.teknikugm.dompetft.revisi.model.Profile_m
import com.teknikugm.dompetft.utama.MainActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_topup_saldo.*
import kotlinx.android.synthetic.main.fragment_profile_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopupSaldo : AppCompatActivity() {

    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager
    var profilResponse: Profile_m? = Profile_m("","",null,null)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topup_saldo)

//        swipe_refreshtopup.setOnRefreshListener{

//
//        }
        sessionManager = SessionManager(this)
            if (sessionManager.fetchAuthToken() == null) {
                txtid.text = "Guest"
            }
            else {
                val detailProfile = sessionManager.getProfile()
                txtid.text = detailProfile.id
            }

        btn_send_topup.setOnClickListener(){

            val username = 10
            val saldo = 37000
            topUp(topup_saldo.text.toString().toInt(),txtid.text.toString().toInt())
//            val dd = txtid.text.toString()
//            Toast.makeText(this, "id user :$dd", Toast.LENGTH_SHORT).show()
        }
    }

    private fun  topUp(jumlahtopup :Int, iduser : Int){

        ApiClient().getApiService(this).topUpNEW(jumlahtopup,iduser)
                .enqueue(object : retrofit2.Callback<ResponseTopup>{
                    override fun onResponse(call: Call<ResponseTopup>, response: Response<ResponseTopup>) {

                        if(response.isSuccessful){
                            if (response.isSuccessful){
                                Toast.makeText(this@TopupSaldo, "topup berhasil", Toast.LENGTH_SHORT).show()

                            }else{
                                Toast.makeText(this@TopupSaldo, "test error", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(this@TopupSaldo, "failed ", Toast.LENGTH_SHORT).show()
                        }

                    }

                    override fun onFailure(call: Call<ResponseTopup>, t: Throwable) {
                        Toast.makeText(this@TopupSaldo, t.message, Toast.LENGTH_SHORT).show()

                    }

                })


    }

    fun getProfile(): Profile_m? {
        apiClient = ApiClient()
        sessionManager = SessionManager(this)
        apiClient.getApiService(this).getProfile()
            .enqueue(object : Callback<Profile_m> {
                override fun onFailure(call: Call<Profile_m>, t: Throwable) {

                }

                //@Suppress("UNREACHABLE_CODE")
                override fun onResponse(call: Call<Profile_m>, response: Response<Profile_m>) {
                    profilResponse = response.body()
                    sessionManager.saveUsername(profilResponse?.username)
                }
            })
        return profilResponse
    }



}