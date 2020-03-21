package com.haxor.techpedia.auth.network

import android.content.Context
import com.haxor.techpedia.auth.model.User
import com.haxor.techpedia.auth.network.config.RetrofitBuilder
import com.haxor.techpedia.konfigurasi.Functions
import retrofit2.Callback

class LoginService(context: Context) {

    private val loginInterface: Functions

    init {
        loginInterface = RetrofitBuilder.builder(context)
            .create(Functions::class.java)
    }

    fun doLogin(email: String, password: String, callback: Callback<User>) {
        loginInterface.login(email, password).enqueue(callback)
    }
}