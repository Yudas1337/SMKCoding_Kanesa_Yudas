package com.haxor.techpedia.konfigurasi

import com.haxor.techpedia.auth.model.User
import com.haxor.techpedia.konfigurasi.sliders.ValueSliders
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Yudas Malabi on 10/08/2019.
 */

interface Functions {

    @POST("modulpopuler.php")
    fun modulpopuler(): Call<Value>


    @POST("modulterbaru.php")
    fun modulterbaru(): Call<Value>

    @POST("slider.php")
    fun ambilslider(): Call<ValueSliders>

    @POST("tampilmenu.php")
    fun tampilmenu(): Call<Value>



    @FormUrlEncoded
    @POST("searchmenu.php")
    fun searchmenu(
        @Field("searchmenu") searchmenu: String ,
        @Field("nama_kategori") nama_kategori: String

    ): Call<Value>

    @FormUrlEncoded
    @POST("listmenu.php")
    fun listmenu(
        @Field("nama_kategori") nama_kategori: String

    ): Call<Value>

    @FormUrlEncoded
    @POST("kategoribab.php")
    fun kategoribab(
        @Field("bahasa") bahasa: String

    ): Call<Value>

    @FormUrlEncoded
    @POST("searchkategori.php")
    fun searchkategori(
        @Field("searchkategori") searchkategori: String ,
        @Field("bahasa") bahasa: String
    ): Call<Value>

    @FormUrlEncoded
    @POST("searchmodules.php")
    fun searchmodules(
        @Field("searchmodules") searchmodules :String ,
        @Field("id_bab") id_bab: String,
        @Field("id_apps") id_apps: String

    ): Call<Value>

    @FormUrlEncoded
    @POST("modules.php")
    fun modules(
        @Field("id_bab") id_bab: String,
        @Field("id_apps") id_apps: String
    ): Call<Value>




    @FormUrlEncoded
    @POST("register.php")
    fun register(
            @Field("nama") nama: String,
            @Field("email") email: String,
            @Field("telepon") telepon: String,
            @Field("password") password: String): Call<Value>

    @FormUrlEncoded
    @POST("tambahulasan.php")
    fun tambahUlasan(
        @Field("ulasan") ulasan: String,
        @Field("id") id: String,
        @Field("id_user") id_user: String): Call<Value>


    @FormUrlEncoded
    @POST("login.php")
    fun login(
            @Field("email") email: String,
            @Field("password") password: String

    ):Call<User>

}

