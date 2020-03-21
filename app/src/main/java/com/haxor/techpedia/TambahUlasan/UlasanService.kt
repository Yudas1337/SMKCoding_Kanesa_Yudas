package com.haxor.techpedia.TambahUlasan

import android.content.Context

import com.haxor.techpedia.auth.network.config.RetrofitBuilder
import com.haxor.techpedia.konfigurasi.Functions
import com.haxor.techpedia.konfigurasi.Value

import retrofit2.Callback

/**
 * Created by Yudas Malabi
 *
 */

class UlasanService(context: Context) {

    private val ulasanInterface: Functions

    init {
        ulasanInterface = RetrofitBuilder.builder(context)
            .create(Functions::class.java)
    }

    fun doUlasan(ulasan: String, id: String , id_user: String , callback: Callback<Value>) {
        ulasanInterface.tambahUlasan(ulasan,id,id_user).enqueue(callback)
    }


}