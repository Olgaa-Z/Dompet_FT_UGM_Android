package com.teknikugm.dompetft.utama

import android.content.ContextWrapper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.teknikugm.dompetft.R
import com.teknikugm.dompetft.retrofit.Constant
import com.teknikugm.dompetft.revisi.Login
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed(Runnable {

            if (getSharedPreferences(Constant.PREFS_NAME, ContextWrapper.MODE_PRIVATE).contains(
                    Constant.username)){
                startActivity(Intent(this, MainActivity::class.java))
            }else {
                startActivity(Intent(this, Login::class.java))
            }

            this@SplashScreen.finish()
        }, 5000)
    }
}