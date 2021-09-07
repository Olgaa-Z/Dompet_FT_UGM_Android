package com.teknikugm.dompetft.revisi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
                usernameregister.error = "Mohon Diisi"
                usernameregister.requestFocus()
                return@setOnClickListener
            }

            if (us.contains(" ",false)){
                usernameregister.error = "Jangan gunakan spasi"
                usernameregister.requestFocus()
                return@setOnClickListener
            }

            if (em.isEmpty()){
                emailregister.error = "Mohon Diisi"
                emailregister.requestFocus()
                return@setOnClickListener
            }

            if (pas1.isEmpty()){
                passwordregister.error = "Mohon Diisi"
                passwordregister.requestFocus()
                return@setOnClickListener
            }

            if (pas2.isEmpty()){
                confirmpasswordregister.error = "Mohon Diisi"
                confirmpasswordregister.requestFocus()
                return@setOnClickListener
            }

            if (pas2!=pas1){
                confirmpasswordregister.error = "password tidak sama"
                confirmpasswordregister.requestFocus()
                return@setOnClickListener
            }

            if (pas1.length < 8){
                passwordregister.error = "Minimal 8 karakter, gunakan kombinasi huruf dan angka"
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
                        Toast.makeText(this@RegisterActivity,"register berhasil", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                        finish()
                    } else {
                        // Error logging in
                        Toast.makeText(this@RegisterActivity,"register gagal\npassword terlalu umum, gunakan kombinasi huruf dan angka", Toast.LENGTH_SHORT).show()
                    }

                }

            })


    }
}