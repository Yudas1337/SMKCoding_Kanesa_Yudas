package com.haxor.techpedia.auth

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.haxor.techpedia.auth.network.RegisterService
import com.haxor.techpedia.konfigurasi.Value
import com.haxor.techpedia.R
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private var registerService: RegisterService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        buttonlogin.setOnClickListener {
            val redirectlogin = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(redirectlogin)
        }

        RegisterButton.setOnClickListener{
            DoRegister()
        }

        ic_backregister.setOnClickListener{
            finish()
        }

    }

    private fun DoRegister()
    {
        val namaInput     = nama.text.toString()
        val emailInput    = email.text.toString()
        val telpInput     = telepon.text.toString()
        val passwordInput = password.text.toString()

        when{
            namaInput.isEmpty() -> nama!!.error = "Nama tidak boleh kosong"
            emailInput.isEmpty()-> email!!.error = "Email tidak boleh kosong"
            telpInput.isEmpty() -> telepon!!.error = "Telepon tidak boleh kosong"
            passwordInput.isEmpty() -> password!!.error = "Password tidak boleh kosong"
            else -> {
                val dialog = ProgressDialog(this)
                dialog.setMessage("Loading ..")
                dialog.show()
                registerService = RegisterService(this)
                registerService!!.doRegister(namaInput, emailInput, telpInput , passwordInput, object : Callback<Value> {

                    override fun onResponse(call: Call<Value>, response: Response<Value>) {
                        val value = response.body() as Value

                        if (value != null) {
                            if (!value.isError) {
                                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                startActivity(intent)
                                this@RegisterActivity.finish()
                            }
                            dialog.dismiss()
                            Toast.makeText(this@RegisterActivity, value.message, Toast.LENGTH_SHORT).show()

                        }

                    }

                    override fun onFailure(call: Call<Value>, t: Throwable) {
                        dialog.dismiss()
                        Toast.makeText(this@RegisterActivity, "terjadi kesalahan , coba lagi !", Toast.LENGTH_SHORT).show()
                    }
                })

            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}