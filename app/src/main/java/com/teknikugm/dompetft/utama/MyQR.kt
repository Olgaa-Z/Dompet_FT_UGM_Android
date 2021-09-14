package com.teknikugm.dompetft.utama

import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.teknikugm.dompetft.R
import com.teknikugm.dompetft.retrofit.Constant
import com.teknikugm.dompetft.revisi.api.ApiClient
import com.teknikugm.dompetft.revisi.api.SessionManager
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_my_q_r.*
import kotlinx.android.synthetic.main.activity_topup_saldo.*

class MyQR : AppCompatActivity() {


    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager

    private var userqr: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_q_r)

        sessionManager = SessionManager(this)
        if (sessionManager.fetchAuthToken() == null) {
            txtid.text = "Guest"
        }
        else {
            val detailProfile = sessionManager.getProfile()
            userqr = detailProfile.username
        }
//        val username = this.getSharedPreferences(Constant.PREFS_NAME, ContextWrapper.MODE_PRIVATE)?.getString(Constant.username, "None").toString()
        if (userqr !== null){
            val bitmap = generateQRCode(userqr.toString())
            img_myqr.setImageBitmap(bitmap)
        }

        panah_myqr.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
        }


    }

    private  val TAG = "MainActivity"
    private fun generateQRCode(text: String): Bitmap {
        val width = 500
        val height = 500
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val codeWriter = MultiFormatWriter()
        try {
            val bitMatrix = codeWriter.encode(text, BarcodeFormat.QR_CODE, width, height)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else resources.getColor(R.color.background))
                }
            }
        } catch (e: WriterException) { Log.d(TAG, "generateQRCode: ${e.message}") }
        return bitmap
    }
}