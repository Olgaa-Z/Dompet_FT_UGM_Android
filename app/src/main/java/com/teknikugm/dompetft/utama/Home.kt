package com.teknikugm.dompetft.utama

import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.teknikugm.dompetft.*
import com.teknikugm.dompetft.pembayaran.Promo
import com.teknikugm.dompetft.retrofit.*
import com.teknikugm.dompetft.revisi.api.ApiClient
import com.teknikugm.dompetft.revisi.api.SessionManager
import com.teknikugm.dompetft.revisi.fragment.ARG_PARAM1
import com.teknikugm.dompetft.revisi.fragment.ARG_PARAM2
import com.teknikugm.dompetft.revisi.fragment.ProfileFragment
import com.teknikugm.dompetft.revisi.model.Profile_m
import com.teknikugm.dompetft.revisi.promo.PromoNew
import com.teknikugm.dompetft.revisi.topup.TopupSaldo
import com.teknikugm.dompetft.revisi.transfer.TransferSaldonew
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.btn_topup_home
import kotlinx.android.synthetic.main.fragment_profile_fragment.*
import retrofit2.Call
import retrofit2.Response
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragmnet.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(com.teknikugm.dompetft.revisi.fragment.ARG_PARAM1)
            param2 = it.getString(com.teknikugm.dompetft.revisi.fragment.ARG_PARAM2)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_send_home.setOnClickListener(){
            startActivity(Intent(context, Scanner_Transfer::class.java))
        }

        btn_topup_home.setOnClickListener(){
            startActivity(Intent(context, TopupSaldo::class.java))
        }

        card_qr.setOnClickListener(){
            startActivity(Intent(context, MyQR::class.java))
        }

        card_transfer.setOnClickListener(){
            startActivity(Intent(context, TransferSaldonew::class.java))
        }

        card_promo.setOnClickListener(){
            startActivity(Intent(context, PromoNew::class.java))
        }

        apiClient = ApiClient()
        sessionManager = SessionManager(this.context!!)

        if (sessionManager.fetchAuthToken() == null) {

        }
        else {
            val activity: MainActivity = activity as MainActivity
            val profile = activity.getProfile()

//            userhome.text = profile?.id
//            usernamehome.text =profile?.username
            txtsaldo_home.text= profile?.saldo

        }


        swipe_refresh.setOnRefreshListener {
//            nampilinSaldo(b)
            sessionManager = SessionManager(this.context!!)
            if (sessionManager.fetchAuthToken() == null) {
                userhome.text = "Guest"
            }
            else {
                val activity: MainActivity = activity as MainActivity
                val profile = activity.getProfile()
//                userhome.text = profile?.id
//                usernamehome.text =profile?.username
                txtsaldo_home.text= profile?.saldo

            }

            swipe_refresh.isRefreshing= false
        }

    }

//    fun nampilinSaldo(key : String?){
//        lateinit var myAPI: API
//        val retrofit = RetrofitClient.instance
//        myAPI = retrofit.create(API::class.java)
//
//        myAPI.getsaldo(key).enqueue(object : retrofit2.Callback<ResponseSaldo>{
//
//            override fun onFailure(call: Call<ResponseSaldo>, t: Throwable) {
//                Toast.makeText(context, "Tidak bisa memuat saldo", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(call: Call<ResponseSaldo>, response: Response<ResponseSaldo>) {
//                val a = response.body()?.balance.toString().toInt()
//                txtsaldo_home.text = Currency.toRupiahFormat2(a).replace("$", "").replace(",", ".")
//            }
//        })
//    }

    //REVISI
//    fun getProfile(): Profile_m? {
//        apiClient = ApiClient()
//        sessionManager = SessionManager(requireContext())
//        apiClient.getApiService(requireContext()).getProfile()
//            .enqueue(object : Callback<Profile_m> {
//                override fun onFailure(call: Call<Profile_m>, t: Throwable) {
//
//                }
//
//                //@Suppress("UNREACHABLE_CODE")
//                override fun onResponse(call: Call<Profile_m>, response: Response<Profile_m>) {
//                    profilResponse = response.body()
//                    sessionManager.saveUsername(profilResponse?.username)
//                }
//            })
//        return profilResponse
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragmnet.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }




}