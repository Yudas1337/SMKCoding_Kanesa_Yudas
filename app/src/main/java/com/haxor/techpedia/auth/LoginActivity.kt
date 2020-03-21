package com.haxor.techpedia.auth

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.haxor.techpedia.auth.model.User
import com.haxor.techpedia.auth.network.LoginService
import com.haxor.techpedia.MainActivity
import com.haxor.techpedia.R
import com.haxor.techpedia.auth.model.Session
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private var loginService: LoginService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ic_backlogin.setOnClickListener{
            finish()
        }

        registerbutton.setOnClickListener {
            val pindah = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(pindah)
        }

        LoginButton.setOnClickListener{
            doLogin()
        }


    }

    private fun doLogin() {
        val email = inputemail.text.toString()
        val password = inputpassword.text.toString()

        when {
            email.isEmpty() -> inputemail!!.error = "Email tidak boleh kosong"
            password.isEmpty() -> inputpassword!!.error = "Password tidak boleh kosong"
            else -> {
                val dialog = ProgressDialog(this)
                dialog.setMessage("Loading ..")
                dialog.show()
                loginService = LoginService(this)
                loginService!!.doLogin(email, password, object : Callback<User> {

                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        val value = response.body() as User
                        if (!value.isError) {
                            Session.putUser(this@LoginActivity, Session.USER_SESSION, value)
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            this@LoginActivity.finish()

                        }
                        dialog.dismiss()
                        Toast.makeText(this@LoginActivity, value.message, Toast.LENGTH_SHORT).show()

                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        dialog.dismiss()
                        Toast.makeText(this@LoginActivity, "terjadi kesalahan , coba lagi !", Toast.LENGTH_SHORT).show()
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

    override fun onBackPressed()
    {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        this@LoginActivity.finish()
    }


}
