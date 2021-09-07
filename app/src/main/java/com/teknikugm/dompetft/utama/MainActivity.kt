package com.teknikugm.dompetft.utama

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.teknikugm.dompetft.R
import com.teknikugm.dompetft.pembayaran.Scanner
import com.teknikugm.dompetft.revisi.api.ApiClient
import com.teknikugm.dompetft.revisi.api.SessionManager
import com.teknikugm.dompetft.revisi.fragment.ProfileFragment
import com.teknikugm.dompetft.revisi.model.Profile_m
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var apiClient: ApiClient
    private lateinit var sessionManager: SessionManager
    var profilResponse: Profile_m? = Profile_m("","",null,null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(R.id.container, Home())
                .commit()

        if (ContextCompat.checkSelfPermission(this@MainActivity,
                Manifest.permission.CAMERA) !==
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity,
                    Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.CAMERA), 1)
            } else {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.CAMERA), 1)
            }
        }

        btnscan.setOnClickListener(){
            startActivity(Intent(this,Scanner::class.java))
        }

        bottomnav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, Home())
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, ProfileFragment())
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }

            }
            false
        }

    }

    //REVISI
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
                    sessionManager.saveProfile(profilResponse)
                }
            })
        return profilResponse
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage("Tutup Aplikasi DompetFT?")
            .setPositiveButton("Ya") { dialog, whichButton ->
                finishAffinity()
            }
            .setNegativeButton("Batal") { dialog, whichButton ->

            }
            .show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@MainActivity,
                            Manifest.permission.CAMERA) ===
                                PackageManager.PERMISSION_GRANTED)) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

}