package com.teknikugm.dompetft.revisi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.teknikugm.dompetft.R
import com.teknikugm.dompetft.revisi.api.ApiClient
import com.teknikugm.dompetft.revisi.api.SessionManager
import com.teknikugm.dompetft.revisi.model.LoginRequest
import com.teknikugm.dompetft.revisi.model.LoginResponse
import com.teknikugm.dompetft.utama.MainActivity
import com.teknikugm.dompetft.utama.Register
import kotlinx.android.synthetic.main.activity_login2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        sessionCheck()




        apiClient = ApiClient()
        sessionManager = SessionManager(this)

        buttonlogin.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)

            val us = usernamelogin.text.toString().trim()
            val pas = passwordlogin.text.toString()

            if (us.isEmpty()){
                usernamelogin.error = "Please fill in"
                usernamelogin.requestFocus()
                return@setOnClickListener
            }

            if (pas.isEmpty()){
                passwordlogin.error = "Please fill in"
                passwordlogin.requestFocus()
                return@setOnClickListener
            }

            val userlogin = LoginRequest(username = us,password = pas)

//            progressBar.visibility = View.VISIBLE


            apiClient.getApiService(this).login(userlogin)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        // Error logging in
                        Toast.makeText(this@Login,"Login Failed \n"+t.toString(),Toast.LENGTH_LONG).show()
//                        progressBar.visibility = View.GONE
                        Log.d("Coba",t.toString())
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        val token = response.body()?.key

                        if (token != null) {
                            sessionManager.saveAuthToken(token)
                            Toast.makeText(this@Login,"Welcome,",Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                            finish()

                        } else {
                            // Error logging in
                            Toast.makeText(this@Login,"wrong username or password",Toast.LENGTH_SHORT).show()
                        }
//                        progressBar.visibility = View.GONE
                    }
                })
            //startActivity(Intent(this, MainActivity2::class.java))
        }

        textregisterlogin.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun sessionCheck(){
        sessionManager = SessionManager(this)
        if (sessionManager.fetchAuthToken() != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press the back button again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}