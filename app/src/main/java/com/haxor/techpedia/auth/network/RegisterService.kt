package com.haxor.techpedia.auth.network

import android.content.Context

import com.haxor.techpedia.auth.network.config.RetrofitBuilder
import com.haxor.techpedia.konfigurasi.Functions
import com.haxor.techpedia.konfigurasi.Value

import retrofit2.Callback

/**
 * Created by Yudas Malabi
 *
 */

class RegisterService(context: Context) {

    private val registerInterface: Functions

    init {
        registerInterface = RetrofitBuilder.builder(context)
            .create(Functions::class.java)
    }

    fun doRegister(nama: String, email: String, telepon: String, password: String, callback: Callback<Value>) {
        registerInterface.register(nama, email, telepon, password).enqueue(callback)
    }


}