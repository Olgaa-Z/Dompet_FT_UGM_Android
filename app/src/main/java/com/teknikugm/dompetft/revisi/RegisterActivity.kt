package com.teknikugm.dompetft.revisi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.teknikugm.dompetft.R
import com.teknikugm.dompetft.revisi.api.ApiClient
import com.teknikugm.dompetft.revisi.model.RegisterRequest
import com.teknikugm.dompetft.revisi.model.RegisterResponse
import kotlinx.android.synthetic.main.activity_register2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        buttonloginregister.setOnClickListener(){
            startActivity(Intent(this, Login::class.java))
        }

        buttonsignupregister.setOnClickListener(){
            val us = usernameregister.text.toString().trim()
            val em = emailregister.text.toString().trim()
            val pas1 = passwordregister.text.toString()
            val pas2 = confirmpasswordregister.text.toString()
            if (us.isEmpty()){
                usernameregister.error = "Please fill in"
                usernameregister.requestFocus()
                return@setOnClickListener
            }

            if (us.contains(" ",false)){
                usernameregister.error = "Don't use spaces"
                usernameregister.requestFocus()
                return@setOnClickListener
            }

            if (em.isEmpty()){
                emailregister.error = "Please fill in"
                emailregister.requestFocus()
                return@setOnClickListener
            }

            if (pas1.isEmpty()){
                passwordregister.error = "Please fill in"
                passwordregister.requestFocus()
                return@setOnClickListener
            }

            if (pas2.isEmpty()){
                confirmpasswordregister.error = "Please fill in"
                confirmpasswordregister.requestFocus()
                return@setOnClickListener
            }

            if (pas2!=pas1){
                confirmpasswordregister.error = "passwords are not the same"
                confirmpasswordregister.requestFocus()
                return@setOnClickListener
            }

            if (pas1.length < 8){
                passwordregister.error = "Minimum 8 characters, use a combination of letters and numbers"
                passwordregister.requestFocus()
                return@setOnClickListener
            }

            register(us,em,pas1,pas2)

        }
    }

    private fun register(Username: String, Email: String, Pass1: String, Pass2: String){
        val intent = Intent(this,Login::class.java)

        val apiClient = ApiClient()
        val userInfo = RegisterRequest(username = Username, email = Email, password1 = Pass1, password2 = Pass2)

        apiClient.getApiService(this).addUser(userInfo)
            .enqueue(object : Callback<RegisterResponse> {
                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity,"register gagal", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    val registerResponse = response.body()

                    if (registerResponse?.key != null) {
                        Toast.makeText(this@RegisterActivity,"registration has been successful", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                        finish()
                    } else {
                        // Error logging in
                        Toast.makeText(this@RegisterActivity,"register Failed\npassword is too general, use a combination of letters and numbers", Toast.LENGTH_SHORT).show()
                    }

                }

            })


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